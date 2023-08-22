package dev.umc.healody.home.controller;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.family.FamilyRequestDTO;
import dev.umc.healody.family.FamilyResponseDTO;
import dev.umc.healody.family.FamilyService;
import dev.umc.healody.family.careuser.dto.CareUserResponseDTO;
import dev.umc.healody.family.careuser.CareUserService;
import dev.umc.healody.home.dto.HomeDto;
import dev.umc.healody.home.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static dev.umc.healody.common.FindUserInfo.getCurrentUserId;
import static dev.umc.healody.common.FindUserInfo.setUserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;
    private final FamilyService familyService;
    private final UserService userService;
    private final CareUserService careUserService;

    @PostMapping("/home") //집 추가 POST
    public SuccessResponse<HomeDto> createHome(@RequestBody HomeDto homeDto){
        setUserService(userService);
        Long adminId = getCurrentUserId();
        HomeDto newHome = homeService.createHome(homeDto, adminId);
        familyService.create(FamilyRequestDTO.builder().userId(adminId).homeId(newHome.homeId).build());
        return new SuccessResponse<>(SuccessStatus.HOME_CREATED, newHome);
    }
    @GetMapping("/home/{userId}") // 집 조회 GET
    public SuccessResponse<Map<String, Map<String, List<String>>>> viewMyFamily(@PathVariable Long userId) {
        List<FamilyResponseDTO> familyList = familyService.searchFamily(userId);

        Map<String, Map<String, List<String>>> resultMap = familyList.stream()
                .collect(Collectors.toMap(
                        family -> homeService.getHomeInfo(family.getHomeId()).getName(),
                        family -> getFamilyInfo(family.getHomeId(), userId)
                ));
        return new SuccessResponse<>(SuccessStatus.HOME_READ, resultMap);
    }
    
    @DeleteMapping("/home/{homeId}") //집 삭제 DELETE
    public SuccessResponse<Void> deleteHome(@PathVariable Long homeId){
        setUserService(userService);
        Long currentUserId = getCurrentUserId();
        Long adminId = homeService.getHomeInfo(homeId).admin;
        if(currentUserId.equals(adminId)) {
            List<Long> family = familyService.searchUserId(homeId);
            for (int i = 0; i < family.size(); i++){
                familyService.delete(family.get(i),homeId);
            }
            homeService.deleteHome(homeId);
            return new SuccessResponse<>(SuccessStatus.HOME_DELETE_SUCCESS);
        }
        return new SuccessResponse<>(SuccessStatus.NOT_HOME_ADMIN);
    }
    @PatchMapping("/home/{homeId}") //집 수정 PATCH
    public SuccessResponse<HomeDto> updateHome(@PathVariable Long homeId, @RequestBody HomeDto homeDto){
        setUserService(userService);
        Long currentUserId = getCurrentUserId();
        Long adminId = homeService.getHomeInfo(homeId).admin;
        if(currentUserId.equals(adminId)) {
            HomeDto updatedHome = homeService.updateHome(homeId, homeDto);
            if (updatedHome != null) {
                return new SuccessResponse<>(SuccessStatus.HOME_UPDATE_SUCCESS, updatedHome);
            }
            return new SuccessResponse<>(SuccessStatus.HOME_UPDATE_FAILURE);
        }
        return new SuccessResponse<>(SuccessStatus.NOT_HOME_ADMIN);
    }

    private Map<String, List<String>> getFamilyInfo(Long homeId, Long userId) {
        List<Long> userList = familyService.searchUserId(homeId);
        List<CareUserResponseDTO> careUserList = careUserService.findCareUsers(homeId);

        Map<String, List<String>> infoMap = new HashMap<>();
        List<String> userInfoList = userList.stream()
                .filter(id -> !id.equals(userId))
                .map(userService::findUser)
                .map(User::toString)
                .collect(Collectors.toList());

        List<String> careUserInfoList = careUserList.stream()
                .map(CareUserResponseDTO::toString)
                .collect(Collectors.toList());

        infoMap.put("user", userInfoList);
        infoMap.put("care-user", careUserInfoList);
        return infoMap;
    }
}