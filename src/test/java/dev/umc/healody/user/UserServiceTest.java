package dev.umc.healody.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void 회원가입() {

        // given
        UserDto dto1 = new UserDto();
        dto1.setName("testOne");
        User user1 = dto1.toEntity(dto1);

        UserDto dto2 = new UserDto();
        dto2.setName("testTwo");
        User user2 = dto2.toEntity(dto2);

        // when
        Long userId1 = userService.join(user1);
        Long userId2 = userService.join(user2);
        System.out.println("userId1 = " + userId1);
        System.out.println("userId2 = " + userId2);

        // then
        assertThat(user1.getName()).isEqualTo(user1.getName());
    }
}