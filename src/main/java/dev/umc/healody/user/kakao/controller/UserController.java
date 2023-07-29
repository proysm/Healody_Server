package dev.umc.healody.user.kakao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.umc.healody.user.kakao.entity.User;
import dev.umc.healody.user.kakao.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

//    @Autowired // 의존성주입(DI)
//    private UserRepository userRepository;

    @GetMapping("/api/auth/kakao/callback")
    public @ResponseBody void kakaoCallback(String code) throws JsonProcessingException {
        // 인증 코드, 카카오 로그인이 성공하면 이곳으로 감, @ResponseBody를 붙이면 데이터를 리턴해주는 함수가 됨.

        User user = userService.kakaoCallback(code); // 현재 로그인을 시도한 사용자의 정보를 리턴함
        kakaoLogin(user); // Healody 로그인을 시도함
    }

    @GetMapping("/api/auth/kakao/login")
    public String kakaoLogin(User user){

        User principal = userService.kakaoLogin(user); // 로그인을 시도함
        if(principal == null) kakaoJoin(user);
        //session.setAttribute("principal", principal);
        return "카카오 로그인이 완료되었습니다.";
    }

    @GetMapping("/api/auth/kakao/join")
    public String kakaoJoin(User newUser){

        userService.kakaoJoin(newUser);
        return "카카오 회원가입이 완료되었습니다.";
    }

}
