package dev.umc.healody.user.controller;

import dev.umc.healody.user.dto.KakaoLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final KakaoLoginDto loginDto;


    @ResponseBody
    @RequestMapping("/kakao/callback/test")
    public KakaoLoginDto kakaoCallbacktest(){
        return loginDto;
    }

    @ResponseBody
    @RequestMapping("/kakao/callback/testing")
    public String kakaoCallbacktesting(@RequestBody String code){
        return code;
    }

    @ResponseBody
    @GetMapping("/please/{code}")
    public String Please(@PathVariable String code){
        return code;
    }
}
