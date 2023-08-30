package dev.umc.healody.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.umc.healody.user.dto.KakaoLoginDto;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.jwt.TokenProvider;
import dev.umc.healody.user.repository.UserRepository;
import dev.umc.healody.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
@RequestMapping("/api/auth")
public class KakaoUserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final KakaoLoginDto loginDto;


    @Autowired
    public KakaoUserController(UserService userService, UserRepository userRepository, KakaoLoginDto kakaoLoginDto, AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider, UserRepository userRepository1, AuthenticationManagerBuilder authenticationManagerBuilder1, TokenProvider tokenProvider1, KakaoLoginDto loginDto) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.loginDto = loginDto;
    }

    @ResponseBody
    @RequestMapping("/kakao/callback/test")
    public KakaoLoginDto kakaoCallbacktest(){
        return loginDto;
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
