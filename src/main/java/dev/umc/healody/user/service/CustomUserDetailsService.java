package dev.umc.healody.user.service;

import dev.umc.healody.user.entity.CustomUserDetails;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String phone) {

        return userRepository.findOneWithAuthoritiesByPhone(phone)
                .map(user -> createUser(phone, user))
                .orElseThrow(() -> new UsernameNotFoundException(phone + " -> 데이터베이스에서 찾을 수 없습니다."));
    }
    private org.springframework.security.core.userdetails.User createUser(String phone, User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(phone + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

//        return new org.springframework.security.core.userdetails.User(user.getPhone(),
//                user.getPassword(),
//                grantedAuthorities);
        return new CustomUserDetails(user.getUserId(), user.getPhone(), user.getPassword(), grantedAuthorities);
    }
}
