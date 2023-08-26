package dev.umc.healody.user.controller;

import dev.umc.healody.user.dto.LoginDto;
import dev.umc.healody.user.dto.TokenDto;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.jwt.JwtFilter;
import dev.umc.healody.user.jwt.TokenProvider;
import dev.umc.healody.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/kakao/login")
public class KakaoLoginController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;

    public KakaoLoginController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
    }


    @RequestMapping
    public ResponseEntity<TokenDto> authorize2(@Valid @ModelAttribute("user") User user) {

        User loginUser = userRepository.findByEmail(user.getEmail());
//        잘 작동 되는 것 확인
        System.out.println(loginUser.getEmail());
        System.out.println(loginUser.getPassword());
        System.out.println(loginUser.getUserId());
        System.out.println(loginUser.getPhone());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                );


        // 여기서 계속 오류 발생 ㅠㅠ
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Long userId = userRepository.findByEmail(loginUser.getEmail()).getUserId();
        String jwt = tokenProvider.createToken(authentication, userId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
        //return null;
    }
}
