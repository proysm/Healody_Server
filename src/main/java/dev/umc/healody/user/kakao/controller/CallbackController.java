package dev.umc.healody.user.kakao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {
    @GetMapping("api/auth/kakao_callback")
    public String callback(){
        return "Testing~~~";
    }
}
