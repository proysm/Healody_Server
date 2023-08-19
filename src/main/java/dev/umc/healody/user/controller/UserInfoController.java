package dev.umc.healody.user.controller;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.common.userInfo;
import dev.umc.healody.user.dto.UpdateUserRequestDto;
import dev.umc.healody.user.dto.UserResponseDto;
import dev.umc.healody.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static dev.umc.healody.common.userInfo.getCurrentUserId;

@RestController
@RequestMapping("/api/user")
public class UserInfoController {
    private final UserService userService;

    @Autowired
    public UserInfoController(UserService userService) {
        this.userService = userService;
        userInfo.userService = userService;
    }

    @GetMapping
    public SuccessResponse<UserResponseDto> getUserInfo(){
        Long userId = getCurrentUserId();
        return new SuccessResponse<>(SuccessStatus.SUCCESS, userService.getUserInfo(userId));
    }

    @PatchMapping
    public SuccessResponse<UserResponseDto> updateUser(@Valid @RequestBody UpdateUserRequestDto request){
        Long userId = getCurrentUserId();
        UserResponseDto updateUser = userService.updateUser(userId, request);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, updateUser);
    }
}
