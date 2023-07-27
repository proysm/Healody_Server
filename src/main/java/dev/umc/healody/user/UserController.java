package dev.umc.healody.user;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/join")
    public SuccessResponse<Long> createUser(@RequestBody UserDto dto) {
        User user = dto.toEntity(dto);
        Long userId = userService.join(user);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, userId);
    }
}
