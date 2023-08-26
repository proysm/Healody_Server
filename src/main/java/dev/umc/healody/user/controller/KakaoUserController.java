package dev.umc.healody.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.user.dto.KakaoLoginDto;
import dev.umc.healody.user.dto.TokenDto;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.jwt.JwtFilter;
import dev.umc.healody.user.jwt.TokenProvider;
import dev.umc.healody.user.repository.UserRepository;
import dev.umc.healody.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;

@Controller
@RequestMapping("/api/auth")
public class KakaoUserController {

    private final UserService userService;
    private final UserRepository userRepository;


    @Autowired
    public KakaoUserController(UserService userService, UserRepository userRepository, KakaoLoginDto kakaoLoginDto, AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider, UserRepository userRepository1, AuthenticationManagerBuilder authenticationManagerBuilder1, TokenProvider tokenProvider1) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @ResponseBody
    @RequestMapping("/kakao/callback")
    public KakaoLoginDto kakaoCallback(String code) throws JsonProcessingException {
        // 인증 코드, 카카오 로그인이 성공하면 이곳으로 감, @ResponseBody를 붙이면 데이터를 리턴해주는 함수가 됨.

        User user = userService.kakaoCallback(code); // 현재 로그인을 시도한 사용자의 정보를 리턴한다.
        Boolean principal = userService.checkEmailDuplication(user.getEmail()); // 존재하는 이메일인지 확인한다.

        // 새로운 유저이면 회원가입을 진행한다.
        if(principal == false){
            userService.kakaoJoin(user);
        }
        User loginUser = userRepository.findByEmail(user.getEmail());

        KakaoLoginDto loginDto = new KakaoLoginDto();
        loginDto.setPhone(loginUser.getPhone());
        loginDto.setPassword(loginUser.getPassword());

        return loginDto;
    }


    @ResponseBody
    @Transactional
    @GetMapping("/kakao/join/getInfo") // kakao 회원가입 이후 반드시 거쳐야됨.
    public SuccessResponse<String> kakaoGetInfo(@RequestParam Long userid, @RequestParam String nickName, @RequestParam String gender, @RequestParam String birth, @RequestParam String phone){

        User newUser = userService.findUser(userid);
        newUser.setNickname(nickName);
        newUser.setGender(gender);
        newUser.setBirth(Date.valueOf(birth));
        newUser.setPhone(phone);
        //userService.kakaoJoin(newUser); @Transactional을 사용하면 굳이 할 필요 없음.

        return new SuccessResponse<>(SuccessStatus.KAKAO_USER_CREATE);
    }

}
