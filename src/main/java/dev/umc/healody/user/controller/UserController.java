package dev.umc.healody.user.controller;

import dev.umc.healody.user.dto.UserDto;
import dev.umc.healody.user.service.EmailService;
import dev.umc.healody.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

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
}
