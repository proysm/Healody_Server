package dev.umc.healody.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.umc.healody.user.dto.UserDto;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.model.KakaoProfile;
import dev.umc.healody.user.model.OAuthToken;
import dev.umc.healody.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
//    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserDto userDTO) {
        // UserDTO에서 필요한 데이터를 추출하여 User 엔티티로 변환
        User user = new User();
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setBirth(userDTO.getBirth());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setNickname(userDTO.getNickname());
        user.setPassword(userDTO.getPassword());

        userRepository.save(user);
    }

//    public JwtToken login(String phone, String password) {
//        User user = userRepository.findByPhone(loginDto.getPhone());
//        System.out.println(user);
//
//        if(user.orElse(null) == null || !){
//            return false;
//        }
//        if(!findUser.getPassword().equals(user.getPassword())){
//            return false;
//        }
//
//        return true;
//    }

    @Transactional(readOnly = true)
    public boolean checkPhoneDuplication(String phone) {
        boolean usernameDuplicate = userRepository.existsByPhone(phone);
        return usernameDuplicate;
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
        RestTemplate rt = new RestTemplate(); // Http 요청을 편리하게 할 수 있다.

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders(); // Http header를 만든다.
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // Http의 body data가 key=value의 data 형태라고 알려준다.

        // body data를 저장한다.
        String clientId = "c33420b52702ac0ebf8805e80e6078f1";
        String redirectUri = "http://localhost:8080/api/auth/kakao/callback";
        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // body data를 저장할 object
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code); // code는 동적임

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담는다.
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // 요청한다.
        ResponseEntity<String> response = rt.exchange(
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
        RestTemplate rt2 = new RestTemplate(); // Http 요청을 편리하게 할 수 있다.

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders(); // Http header를 만든다.
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // Http의 body data가 key=value의 data 형태라고 알려준다.

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담는다.
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        // 요청한다.
        ResponseEntity<String> response2 = rt2.exchange(
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
        String nickName = kakaoProfile.getProperties().getNickname();
        UUID garbagePw = UUID.randomUUID(); // 쓰레기값 만들기

        Date birthday = null;
        String gender = null;
        if(kakaoProfile.getKakao_account().birthday_needs_agreement == true){
            birthday = Date.valueOf(kakaoProfile.getKakao_account().birthyear + kakaoProfile.getKakao_account().birthday);
        }
        if(kakaoProfile.getKakao_account().gender_needs_agreement == true){
            gender = kakaoProfile.getKakao_account().gender;
        }

        user.setUserId(kakaoProfile.getId());
        user.setName(name);
        user.setPhone(null);
        user.setBirth(birthday);
        user.setEmail(email);
        user.setGender(gender);
        user.setNickname(nickName);
        user.setPassword(String.valueOf(garbagePw));

        return user; // 로그인을 한 사용자를 넘겨준다.(정보가 포함되어 있음)

    }

    // 2. 카카오 로그인 시도 -> 이미 가입한 사용자인지 확인하고 새로운 사용자라면 회원가입으로 이동한다.
    @Transactional(readOnly = true)
    public User kakaoLogin(User user){

        //User principal = new User();
        User principal = userRepository.findByUserId(user.getUserId());
        if(principal != null){

            // 로그인 구현해야 됨.
        }
        return principal;
    }

    // 3. 카카오 회원가입 -> 추가 정보를 입력한다.
    @Transactional
    public void kakaoJoin(User newUser){


        // 추가 정보를 받아야 함.

        userRepository.save(newUser);
    }
}
