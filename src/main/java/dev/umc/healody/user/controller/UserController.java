package dev.umc.healody.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.user.dto.UserDto;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.service.EmailService;
import dev.umc.healody.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;


@Controller
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    String confirm;


    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @ResponseBody
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

    @ResponseBody
    @GetMapping("/phone/{phone}/exists")
    public SuccessResponse<String> checkPhoneDuplicate(@PathVariable String phone){
        if(userService.checkPhoneDuplication(phone)){
//            return new SuccessResponse<>(SuccessStatus.FAILURE, "이미 존재하는 휴대폰 번호입니다.");
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "이미 존재하는 휴대폰 번호입니다.");

        } else {
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "사용 가능한 휴대폰 번호입니다.");
        }
    }

    @ResponseBody
    @GetMapping("/nickname/{nickname}/exists")
    public SuccessResponse<String> checkNicknameDuplicate(@PathVariable String nickname){
        if(userService.checkNicknameDuplication(nickname)){
//            return new SuccessResponse<>(SuccessStatus.FAILURE, "이미 존재하는 닉네임입니다.");
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "이미 존재하는 닉네임입니다.");
        } else {
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "사용 가능한 닉네임입니다.");
        }
    }

    @ResponseBody
    @GetMapping("/email/{email}/exists")
    public SuccessResponse<String> checkEmailDuplicate(@PathVariable String email){
        if(userService.checkEmailDuplication(email)){
//            return new SuccessResponse<>(SuccessStatus.FAILURE, "이미 존재하는 이메일입니다.");
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "이미 존재하는 이메일입니다.");
        } else {
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "사용 가능한 이메일입니다.");
        }
    }

    @ResponseBody
    @PostMapping("/email-confirm")
    public SuccessResponse<String> emailConfirm(@RequestParam String email) throws Exception {
        confirm = emailService.sendEmail(email);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, "이메일을 확인해주세요");
    }

    @ResponseBody
    @PostMapping("/email-confirm/check")
    public SuccessResponse<String> emailConfirmCheck(@RequestParam String check) {
        if(confirm.equals(check)) {
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "이메일 인증이 완료되었습니다.");
        } else {
//            return new SuccessResponse<>(SuccessStatus.FAILURE, "이메일 인증 코드가 일치하지 않습니다.");
            return new SuccessResponse<>(SuccessStatus.SUCCESS, "이메일 인증 코드가 일치하지 않습니다.");
        }
    }

    @ResponseBody
    @GetMapping("/user-id/{phone}")
    public SuccessResponse<Long> findUserIdByPhone(@PathVariable String phone) {
        // 입력 받은 휴대폰 번호로 유저 아이디를 조회
        Long userId = userService.findUserIdByPhone(phone);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, userId);
    }


    @RequestMapping("/kakao/callback")
    public String kakaoCallback(String code, RedirectAttributes rttr) throws JsonProcessingException {
        // 인증 코드, 카카오 로그인이 성공하면 이곳으로 감, @ResponseBody를 붙이면 데이터를 리턴해주는 함수가 됨.

        User user = userService.kakaoCallback(code); // 현재 로그인을 시도한 사용자의 정보를 리턴함

        rttr.addFlashAttribute("user", user);
        return "redirect:/api/auth/kakao/login";
    }

    @RequestMapping("/kakao/login")
    public String kakaoLogin(@ModelAttribute("user") User user, RedirectAttributes rttr){

        User newUser = user;

        Boolean principal = userService.kakaoLogin(newUser); // 로그인을 시도한다.
        if(principal == false){ // 새로운 유저이면 회원가입을 진행한다.
            rttr.addFlashAttribute("newUser", newUser);
            return "redirect:/api/auth/kakao/join";
        }
        // 이미 존재하는 유저이면 로그인을 진행한다.
        return null;
    }

    @ResponseBody
    @GetMapping("/kakao/join") // 일단 가입을 시킨 다음, 'kakaoGetInfo'에서 추가 정보를 입력받는다.
    public void kakaoJoin(@ModelAttribute("newUser") User newUser, RedirectAttributes rttr){
        userService.kakaoJoin(newUser);
        rttr.addFlashAttribute("newUserId", newUser.getUserId());
        //return "redirect:/api/auth/kakao/join/getInfo";
    }


    @ResponseBody
    @Transactional
    @GetMapping("/kakao/join/getInfo") // kakao 회원가입 이후 반드시 거쳐야됨.
    public void kakaoGetInfo(@RequestParam Long userid, @RequestParam String nickName, @RequestParam String gender, @RequestParam String birth, @RequestParam String phone){

        User newUser = userService.findUser(userid);
        newUser.setNickname(nickName);
        newUser.setGender(gender);
        newUser.setBirth(Date.valueOf(birth));
        newUser.setPhone(phone);
        //userService.kakaoJoin(newUser); @Transactional을 사용하면 굳이 할 필요 없음.
    }

    @ResponseBody
    @PostMapping("/kakao/logout")
    public String kakaoLogout(User newUser){

        userService.kakaoLogout(newUser);
        return "카카오 로그아웃이 완료되었습니다.";
    }


}
