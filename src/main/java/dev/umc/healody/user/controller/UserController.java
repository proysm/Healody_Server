package dev.umc.healody.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.user.dto.UserDto;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.service.EmailService;
import dev.umc.healody.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    String confirm;

    @Autowired
    private HttpSession session;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/join")
    public SuccessResponse<String> registerUser(@Valid @RequestBody UserDto userDTO) {
        userService.registerUser(userDTO);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, "회원가입이 완료되었습니다.");
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, Errors errors) {
//        JwtToken token = userService.login(loginDto.getPhone(), loginDto.getPassword())
//        return ResponseEntity.ok(token);
//    }

    @GetMapping("/phone/{phone}/exists")
    public SuccessResponse<String> checkPhoneDuplicate(@PathVariable String phone){
        if(userService.checkPhoneDuplication(phone)){
//            return new SuccessResponse<>(SuccessStatus.FAILURE, "이미 존재하는 휴대폰 번호입니다.");
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "이미 존재하는 휴대폰 번호입니다.");

        } else {
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "사용 가능한 휴대폰 번호입니다.");
        }
    }

    @GetMapping("/nickname/{nickname}/exists")
    public SuccessResponse<String> checkNicknameDuplicate(@PathVariable String nickname){
        if(userService.checkNicknameDuplication(nickname)){
//            return new SuccessResponse<>(SuccessStatus.FAILURE, "이미 존재하는 닉네임입니다.");
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "이미 존재하는 닉네임입니다.");
        } else {
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "사용 가능한 닉네임입니다.");
        }
    }

    @GetMapping("/email/{email}/exists")
    public SuccessResponse<String> checkEmailDuplicate(@PathVariable String email){
        if(userService.checkEmailDuplication(email)){
//            return new SuccessResponse<>(SuccessStatus.FAILURE, "이미 존재하는 이메일입니다.");
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "이미 존재하는 이메일입니다.");
        } else {
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "사용 가능한 이메일입니다.");
        }
    }

    @PostMapping("/email-confirm")
    public SuccessResponse<String> emailConfirm(@RequestParam String email) throws Exception {
        confirm = emailService.sendEmail(email);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, "이메일을 확인해주세요");
    }

    @PostMapping("/email-confirm/check")
    public SuccessResponse<String> emailConfirmCheck(@RequestParam String check) {
        if(confirm.equals(check)) {
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "이메일 인증이 완료되었습니다.");
        } else {
//            return new SuccessResponse<>(SuccessStatus.FAILURE, "이메일 인증 코드가 일치하지 않습니다.");
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "이메일 인증 코드가 일치하지 않습니다.");
        }
    }

    @GetMapping("/user-id/{phone}")
    public SuccessResponse<Long> findUserIdByPhone(@PathVariable String phone) {
        // 입력 받은 휴대폰 번호로 유저 아이디를 조회
        Long userId = userService.findUserIdByPhone(phone);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, userId);
    }

    @GetMapping("/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) throws JsonProcessingException {
        // 인증 코드, 카카오 로그인이 성공하면 이곳으로 감, @ResponseBody를 붙이면 데이터를 리턴해주는 함수가 됨.

        User user = userService.kakaoCallback(code); // 현재 로그인을 시도한 사용자의 정보를 리턴함
        kakaoLogin(user); // 로그인을 시도함

        return "카카오 코드와 토큰이 발급되었습니다.";
    }

    @GetMapping("/kakao/login")
    public String kakaoLogin(User user){

        User principal = userService.kakaoLogin(user); // 로그인을 시도함
        if(principal == null) kakaoJoin(user);
        //session.setAttribute("principal", principal);
        return "카카오 로그인이 완료되었습니다.";
    }

    @GetMapping("/kakao/join")
    public String kakaoJoin(User newUser){

        userService.kakaoJoin(newUser);
        return "카카오 회원가입이 완료되었습니다.";
    }
}
