package dev.umc.healody.user.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final Long userId;  // 추가 정보로 userId를 포함

    public CustomUserDetails(Long userId, String phone, String password, Collection<? extends GrantedAuthority> authorities) {
        super(phone, password, authorities);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}