package dev.umc.healody.user.kakao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/api/auth/kakao/callback")
    // 인가 코드, 카카오 로그인이 성공하면 이곳으로 감, @ResponseBody를 붙이면 데이터를 리턴해주는 함수가 됨.
    public @ResponseBody String kakaoCallback(String code){

        // 토큰 발급 구현 예정

        return "카카오 인증 완료, 코드 값 : " + code;
    }




}
