package dev.umc.healody.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.umc.healody.user.dto.*;
import dev.umc.healody.user.entity.Authority;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.jwt.JwtFilter;
import dev.umc.healody.user.jwt.TokenProvider;
import dev.umc.healody.user.model.KakaoProfile;
import dev.umc.healody.user.model.OAuthToken;
import dev.umc.healody.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.oauth2.client.registration.kakao.client-id}")
    String clientId;
    @Value("${security.oauth2.client.registration.kakao.redirect-uri}")
    String redirectUri;
    @Value("${security.oauth2.client.registration.kakao.kakaoPw}")
    String kakaoPw;


    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    public void registerUser(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByPhone(userDto.getPhone()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        // 가입되어 있지 않은 회원이면 권한 정보 만들기
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // UserDTO에서 필요한 데이터를 추출하여 User 엔티티로 변환
        User user = new User();
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setBirth(userDto.getBirth());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setNickname(userDto.getNickname());
//        user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode((userDto.getPassword())));
        System.out.println(passwordEncoder.encode((userDto.getPassword())));
        user.setActivated(true);
        user.setAuthorities(Collections.singleton(authority));

        userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public boolean checkPhoneDuplication(String phone) {
        boolean phoneDuplicate = userRepository.existsByPhone(phone);
        return phoneDuplicate;
    }

    @Transactional(readOnly = true)
    public boolean checkNicknameDuplication(String nickname) {
        boolean nicknameDuplicate = userRepository.existsByNickname(nickname);
        return nicknameDuplicate;
    }

    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email) {
        boolean emailDuplicate = userRepository.existsByEmail(email);
        return emailDuplicate;
    }

    @Transactional(readOnly = true)
    public Long findUserIdByPhone(String phone) {
        User user = userRepository.findByPhone(phone);
        if (user != null) {
            return user.getUserId();
        }
        return null; // 해당 전화번호로 유저를 찾지 못한 경우
    }

    // 1. 카카오 로그인을 시도한 후 성공하면 실행
    public @ResponseBody User kakaoCallback(String code) throws JsonProcessingException {

        // 1. Post방식으로 key=value 데이터를 카카오쪽으로 요청한다.
        RestTemplate tokenRt = new RestTemplate(); // Http 요청을 편리하게 할 수 있다.

        // HttpHeader 오브젝트 생성
        HttpHeaders tokenHeader = new HttpHeaders(); // Http header를 만든다.
        tokenHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // Http의 body data가 key=value의 data 형태라고 알려준다.

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // body data를 저장할 object
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code); // code는 동적임

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담는다.
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, tokenHeader);

        // 요청한다.
        ResponseEntity<String> response = tokenRt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST, // 요청 타입
                kakaoTokenRequest, // Http body에 들어갈 data와 header 값
                String.class // 응답 타입
        );

        // json data를 오브젝트에 담는다. (Gson, Json Simple, ObjectMapper 등)
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try{
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        }catch(JsonMappingException e){
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());


        // 2. Post방식으로 key=value 데이터를 카카오쪽으로 요청한다.
        RestTemplate userRt = new RestTemplate(); // Http 요청을 편리하게 할 수 있다.

        // HttpHeader 오브젝트 생성
        HttpHeaders userHeader = new HttpHeaders(); // Http header를 만든다.
        userHeader.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        userHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // Http의 body data가 key=value의 data 형태라고 알려준다.

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담는다.
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(userHeader);

        // 요청한다.
        ResponseEntity<String> response2 = userRt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST, // 요청 타입
                kakaoProfileRequest, // header 값
                String.class // 응답 타입
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try{
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        }catch(JsonMappingException e){
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        // 3. User 오브젝트를 만든다. (사용자의 정보를 넘겨주기 위한 작업)
        User user = new User();

        String name = kakaoProfile.getProperties().getNickname();
        String email = kakaoProfile.getKakao_account().getEmail();
        String image = kakaoProfile.getProperties().getThumbnail_image();


        //user.setUserId(kakaoProfile.getId());
        user.setName(name);
        user.setEmail(email);
        user.setImage(image);
        user.setPhone(null);
        user.setBirth(null);
        user.setGender(null);
        user.setNickname(null);


        return user; // 로그인을 한 사용자를 넘겨준다.(정보가 포함되어 있음)

    }


    // 2. 카카오 회원가입
    @Transactional
    public void kakaoJoin(User newUser){

        // 가입되어 있지 않은 회원이면 권한 정보 만들기
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

//        Random r = new Random();    // 쓰레기값 만들기
//        RandomString rs = new RandomString(16, r);
//        String garbagePw = rs.nextString();

        newUser.setPassword(passwordEncoder.encode((kakaoPw))); // 프론트에게 kakaoPw 알려주기
        newUser.setActivated(true);
        newUser.setAuthorities(Collections.singleton(authority));

        userRepository.save(newUser);
    }


    @Transactional
    public User findUser(Long userId){
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public User findUser(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public UserResponseDto getUserInfo(Long userId){
        User user = userRepository.findByUserId(userId);
        return UserResponseDto.builder()
                .name(user.getName())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .birth(user.getBirth())
                .email(user.getEmail())
                .gender(user.getGender())
                .image(user.getImage())
                .message(user.getMessage())
                .familyCnt(user.getFamilyCnt())
                .build();
    }

    // 유저,권한 정보를 가져오는 메소드
//    @Transactional(readOnly = true)
//    public Optional<User> getUserWithAuthorities(String phone) {
//        return userRepository.findOneWithAuthoritiesByPhone(phone);
//    }

    public UpdateProfileDto updateProfile(Long userId, UpdateProfileDto userDto) {
        Optional<User> existUser = userRepository.findById(userId);
        User user = new User();
        if(existUser.isPresent()){
            user = existUser.get();
        }
        if(userDto.getNickname() != null){
            user.setNickname(userDto.getNickname());
        }
        if(userDto.getImage() != null){
            user.setImage(userDto.getImage());
        }

        User updatedUser = userRepository.save(user);

        return UpdateProfileDto.builder()
                .nickname(updatedUser.getNickname())
                .image(updatedUser.getImage())
                .build();
    }

    public UpdateInfoDto updateInfo(Long userId, UpdateInfoDto userDto) {
        Optional<User> existUser = userRepository.findById(userId);
        User user = new User();
        if(existUser.isPresent()){
            user = existUser.get();
        }
        if(userDto.getEmail() != null){
            user.setEmail(userDto.getEmail());
        }
        if(userDto.getPassword() != null){
            user.setPassword(passwordEncoder.encode((userDto.getPassword())));
        }

        User updatedUser = userRepository.save(user);

        return UpdateInfoDto.builder()
                .email(updatedUser.getEmail())
                .password(updatedUser.getPassword())
                .build();
    }

    public UpdateMessageDto updateMessage(Long userId, UpdateMessageDto userDto) {
        Optional<User> existUser = userRepository.findById(userId);
        User user = new User();
        if(existUser.isPresent()){
            user = existUser.get();
        }
        if(userDto.getMessage() != null){
            user.setMessage(userDto.getMessage());
        }

        User updatedUser = userRepository.save(user);

        return UpdateMessageDto.builder()
                .message(updatedUser.getMessage())
                .build();
    }

    @Transactional
    public boolean checkMemberPassword(String inputPassword, Long userId) {
        return passwordEncoder.matches(inputPassword, userRepository.findByUserId(userId).getPassword());
    }
}
