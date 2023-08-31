package dev.umc.healody.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.user.dto.KakaoLoginDto;
import dev.umc.healody.user.dto.TestDto;
import dev.umc.healody.user.dto.UserDto;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.repository.UserRepository;
import dev.umc.healody.user.service.EmailService;
import dev.umc.healody.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;


@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final KakaoLoginDto loginDto;
    private final TestDto testDto;
    String confirm = "";


    @Autowired
    public UserController(UserService userService, EmailService emailService, UserRepository userRepository, KakaoLoginDto loginDto, TestDto testDto) {
        this.userService = userService;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.loginDto = loginDto;
        this.testDto = testDto;
    }

    @ResponseBody
    @PostMapping("/join")
    public SuccessResponse<String> registerUser(@Valid @RequestBody UserDto userDTO) {
        userService.registerUser(userDTO);
        return new SuccessResponse<>(SuccessStatus.USER_CREATE);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, Errors errors) {
//        JwtToken token = userService.login(loginDto.getPhone(), loginDto.getPassword())
//        return ResponseEntity.ok(token);
//    }

    @ResponseBody
    @GetMapping("/phone/{phone}/exists")
    public SuccessResponse<String> checkPhoneDuplicate(@PathVariable String phone){
        if(userService.checkPhoneDuplication(phone)){
            return new SuccessResponse<>(SuccessStatus.PHONE_FAILURE);

        } else {
            return new SuccessResponse<>(SuccessStatus.PHONE_SUCCESS);
        }
    }

    @ResponseBody
    @GetMapping("/nickname/{nickname}/exists")
    public SuccessResponse<String> checkNicknameDuplicate(@PathVariable String nickname){
        if(userService.checkNicknameDuplication(nickname)){
            return new SuccessResponse<>(SuccessStatus.NICKNAME_FAILURE);
        } else {
            return new SuccessResponse<>(SuccessStatus.NICKNAME_SUCCESS);
        }
    }

    @ResponseBody
    @GetMapping("/email/{email}/exists")
    public SuccessResponse<String> checkEmailDuplicate(@PathVariable String email){
        if(userService.checkEmailDuplication(email)){
            return new SuccessResponse<>(SuccessStatus.EMAIL_FAILURE);
        } else {
            return new SuccessResponse<>(SuccessStatus.EMAIL_SUCCESS);
        }
    }

    @ResponseBody
    @PostMapping("/email-confirm")
    public SuccessResponse<String> emailConfirm(@RequestParam String email) throws Exception {
        confirm = emailService.sendEmail(email);
        return new SuccessResponse<>(SuccessStatus.EMAIL_CONFIRM_SUCCESS);
    }

    @ResponseBody
    @PostMapping("/email-confirm/check")
    public SuccessResponse<String> emailConfirmCheck(@RequestParam String check) {
        if(confirm.equals(check)) {
            return new SuccessResponse<>(SuccessStatus.EMAIL_CONFIRM_CHECK_SUCCESS);
        } else {
            return new SuccessResponse<>(SuccessStatus.EMAIL_CONFIRM_CHECK_FAILURE);
        }
    }

//    @ResponseBody
//    @GetMapping("/user-id/{phone}")
//    public SuccessResponse<Long> findUserIdByPhone(@PathVariable String phone) {
//        // 입력 받은 휴대폰 번호로 유저 아이디를 조회
//        Long userId = userService.findUserIdByPhone(phone);
//        return new SuccessResponse<>(SuccessStatus.SUCCESS, userId);
//    }


    // 수민
    @ResponseBody
    @GetMapping("/kakao/callbacks") //KakaoLoginDto
    public TestDto kakaoCallback(@RequestBody TestDto code) throws JsonProcessingException {
        // 인증 코드, 카카오 로그인이 성공하면 이곳으로 감, @ResponseBody를 붙이면 데이터를 리턴해주는 함수가 됨.

        //testDto.setStr(code);
        return code;
        // User user = userService.kakaoCallback(code); // 현재 로그인을 시도한 사용자의 정보를 리턴한다.
        // Boolean principal = userService.checkEmailDuplication(user.getEmail()); // 존재하는 이메일인지 확인한다.


        // // 새로운 유저이면 회원가입을 진행한다.ㅋ
        // if(principal == false){
        //     userService.kakaoJoin(user);
        //     loginDto.setStatus(false);
        // }
        // else{
        //     loginDto.setStatus(true);
        //     User loginUser = userRepository.findByEmail(user.getEmail());
        //     loginDto.setPhone(loginUser.getPhone());
        //     loginDto.setPassword(loginUser.getPassword());
        // }
        // return loginDto;

    }


    @ResponseBody
    @Transactional
    @GetMapping("/kakao/join/getInfo") // kakao 회원가입 이후 반드시 거쳐야됨.
    public KakaoLoginDto kakaoGetInfo(@RequestParam Long userid, @RequestParam String nickName, @RequestParam String gender, @RequestParam String birth, @RequestParam String phone){

        User newUser = userService.findUser(userid);
        newUser.setNickname(nickName);
        newUser.setGender(gender);
        newUser.setBirth(Date.valueOf(birth));
        newUser.setPhone(phone);
        //userService.kakaoJoin(newUser); @Transactional을 사용하면 굳이 할 필요 없음.

        KakaoLoginDto loginDto = new KakaoLoginDto();
        loginDto.setPhone(newUser.getPhone());
        loginDto.setStatus(true);
        loginDto.setPassword(newUser.getPassword());

        return loginDto;
    }






}
