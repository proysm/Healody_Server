package dev.umc.healody.common;

import dev.umc.healody.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FindUserInfo {

    public static UserService userService;

    public static void setUserService(UserService userService) {
        FindUserInfo.userService = userService;
    }
    //현재 로그인된 사용자의 userId 반환
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication != null) && (authentication.getPrincipal() instanceof UserDetails)) {
            String userName = authentication.getName();
            Long userId = userService.findUserIdByPhone(userName);
            return userId;
        }
        return null; // 인증된 사용자가 없을 경우 null 반환
    }
}
