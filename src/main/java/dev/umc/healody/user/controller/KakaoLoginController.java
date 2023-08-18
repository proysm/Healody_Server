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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public class KakaoLoginController {

    @RequestMapping("/api/auth/kakao/login")
    public class LoginController {
        private final TokenProvider tokenProvider;
        private final AuthenticationManagerBuilder authenticationManagerBuilder;
        private final UserRepository userRepository;

        public LoginController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository) {
            this.tokenProvider = tokenProvider;
            this.authenticationManagerBuilder = authenticationManagerBuilder;
            this.userRepository = userRepository;
        }

        @PostMapping
        public ResponseEntity<TokenDto> authorize(@Valid @RequestBody @ModelAttribute("user") User user) {

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            user.getPhone(),
                            user.getPassword()
                    );

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Long userId = userRepository.findByPhone(user.getPhone()).getUserId();
            String jwt = tokenProvider.createToken(authentication, userId);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

            return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
        }
    }
}
