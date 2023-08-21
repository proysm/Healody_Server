package dev.umc.healody.user.controller;

import dev.umc.healody.common.FindUserInfo;
import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.user.dto.UpdateInfoDto;
import dev.umc.healody.user.dto.UpdateMessageDto;
import dev.umc.healody.user.dto.UpdateProfileDto;
import dev.umc.healody.user.dto.UserResponseDto;
import dev.umc.healody.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static dev.umc.healody.common.FindUserInfo.getCurrentUserId;

@RestController
@RequestMapping("/api/user")
public class UserInfoController {
    private final UserService userService;

    @Autowired
    public UserInfoController(UserService userService) {
        this.userService = userService;
        FindUserInfo.userService = userService;
    }

    @GetMapping
    public SuccessResponse<UserResponseDto> getUserInfo(){
        Long userId = getCurrentUserId();
        UserResponseDto user = userService.getUserInfo(userId);
        return new SuccessResponse<>(SuccessStatus.USER_GET, user);
    }

    @PatchMapping("/profile")
    public SuccessResponse<UpdateProfileDto> updateProfile(@Valid @RequestBody UpdateProfileDto request){
        Long userId = getCurrentUserId();
        UpdateProfileDto updateUser = userService.updateProfile(userId, request);
        return new SuccessResponse<>(SuccessStatus.PROFILE_UPDATE, updateUser);
    }

    @PatchMapping("/message")
    public SuccessResponse<UpdateMessageDto> updateMessage(@Valid @RequestBody UpdateMessageDto request) {
        Long userId = getCurrentUserId();
        UpdateMessageDto updateUser = userService.updateMessage(userId, request);
        return new SuccessResponse<>(SuccessStatus.MESSAGE_UPDATE, updateUser);
    }

    @PatchMapping("/info")
    public SuccessResponse<UpdateInfoDto> updateInfo(@Valid @RequestBody UpdateInfoDto request) {
        Long userId = getCurrentUserId();
        UpdateInfoDto updateUser = userService.updateInfo(userId, request);
        return new SuccessResponse<>(SuccessStatus.INFO_UPDATE, updateUser);
    }

    @PostMapping("/password/check")
    public SuccessResponse<?> checkPassword(@RequestBody Map<String, Object> request) {
        Long userId = getCurrentUserId();
        if(userService.checkMemberPassword(request.get("password").toString(), userId)){
            return new SuccessResponse<>(SuccessStatus.PASSWORD_SUCCESS);
        } else {
            return new SuccessResponse<>(SuccessStatus.PASSWORD_FAILURE);
        }
    }

}
