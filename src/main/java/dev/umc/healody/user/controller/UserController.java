package dev.umc.healody.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.umc.healody.user.dto.UserDto;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.service.EmailService;
import dev.umc.healody.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;


    private HttpSession session;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, Errors errors) {
//        JwtToken token = userService.login(loginDto.getPhone(), loginDto.getPassword())
//        return ResponseEntity.ok(token);
//    }

    @GetMapping("/phone/{phone}/exists")
    public ResponseEntity<Boolean> checkPhoneDuplicate(@PathVariable String phone){
        return ResponseEntity.ok(userService.checkPhoneDuplication(phone));
    }

    @GetMapping("/nickname/{nickname}/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname){
        return ResponseEntity.ok(userService.checkNicknameDuplication(nickname));
    }

    @GetMapping("/email/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email){
        return ResponseEntity.ok(userService.checkEmailDuplication(email));
    }

    @PostMapping("/email-confirm")
    public String emailConfirm(@RequestParam String email) throws Exception {
        String confirm = emailService.sendEmail(email);
        return confirm;
    }

    @GetMapping("/user-id/{phone}")
    public ResponseEntity<Long> findUserIdByPhone(@PathVariable String phone) {
        // 입력 받은 휴대폰 번호로 유저 아이디를 조회
        Long userId = userService.findUserIdByPhone(phone);
        return ResponseEntity.ok(userId);
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

    @PostMapping("/kakao/logout")
    public String kakaoLogout(User newUser){

        userService.kakaoLogout(newUser);
        return "카카오 로그아웃이 완료되었습니다.";
    }
}
