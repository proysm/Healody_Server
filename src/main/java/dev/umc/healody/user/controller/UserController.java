package dev.umc.healody.user.controller;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.user.dto.KakaoLoginDto;
import dev.umc.healody.user.dto.ingaDto;
import dev.umc.healody.user.dto.UserDto;
import dev.umc.healody.user.repository.UserRepository;
import dev.umc.healody.user.service.EmailService;
import dev.umc.healody.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    String confirm = "";


    @Autowired
    public UserController(UserService userService, EmailService emailService, UserRepository userRepository, KakaoLoginDto loginDto, ingaDto ingaDto) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @ResponseBody
    @PostMapping("/join")
    public SuccessResponse<String> registerUser(@Valid @RequestBody UserDto userDTO) {
        userService.registerUser(userDTO);
        return new SuccessResponse<>(SuccessStatus.USER_CREATE);
    }

    @ResponseBody
    @PostMapping("/joins")
    public SuccessResponse<String> registerUsers(@Valid @RequestBody UserDto userDTO) {
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


}
