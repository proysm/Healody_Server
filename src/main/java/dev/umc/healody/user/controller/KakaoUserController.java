package dev.umc.healody.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.service.EmailService;
import dev.umc.healody.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;

@Controller
@RequestMapping("/api/auth")
public class KakaoUserController {

    private final UserService userService;
    @Autowired
    public KakaoUserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/kakao/callback")
    public String kakaoCallback(String code, RedirectAttributes rttr) throws JsonProcessingException {
        // 인증 코드, 카카오 로그인이 성공하면 이곳으로 감, @ResponseBody를 붙이면 데이터를 리턴해주는 함수가 됨.

        User user = userService.kakaoCallback(code); // 현재 로그인을 시도한 사용자의 정보를 리턴한다.
        Boolean principal = userService.kakaoLogin(user); // 로그인을 시도한다.

        // 새로운 유저이면 회원가입을 진행한다.
        if(principal == false){
            rttr.addFlashAttribute("newUser", user);
            return "redirect:/api/auth/kakao/join";
        }
        // 이미 존재하는 유저이면 로그인을 진행한다.
        else{
            rttr.addFlashAttribute("user", user);
            return "redirect:/api/auth/kakao/login";
        }

    }


    @ResponseBody
    @GetMapping("/kakao/join") // 일단 가입을 시킨 다음, 'kakaoGetInfo'에서 추가 정보를 입력받는다.
    public void kakaoJoin(@ModelAttribute("newUser") User newUser, RedirectAttributes rttr){
        userService.kakaoJoin(newUser);
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

//    @ResponseBody
//    @PostMapping("/kakao/logout")
//    public String kakaoLogout(User newUser){
//
//        userService.kakaoLogout(newUser);
//        return "카카오 로그아웃이 완료되었습니다.";
//    }
    // 토큰 발급을 끊어서 로그아웃 시킨다.


}
