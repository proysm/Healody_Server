package dev.umc.healody.home.controller;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.family.FamilyRequestDTO;
import dev.umc.healody.family.FamilyResponseDTO;
import dev.umc.healody.family.FamilyService;
import dev.umc.healody.family.careuser.CareUserResponseDTO;
import dev.umc.healody.family.careuser.CareUserService;
import dev.umc.healody.home.dto.HomeDto;
import dev.umc.healody.home.service.HomeService;
import dev.umc.healody.user.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static dev.umc.healody.common.userInfo.getCurrentUserId;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;
    private final FamilyService familyService;
    private final UserService userService;
    private final CareUserService careUserService;

    @PostMapping("/home") //집 추가 POST
    public SuccessResponse<HomeDto> createHome(@RequestBody HomeDto homeDto, HttpServletRequest request){
        Long adminId = getCurrentUserId();
        HomeDto newHome = homeService.createHome(homeDto, adminId);
        familyService.create(FamilyRequestDTO.builder().userId(adminId).homeId(newHome.homeId).build());
        return new SuccessResponse<>(SuccessStatus.SUCCESS, newHome);
    }
    @GetMapping("/home/{userId}") // 집 조회 GET
    public ResponseEntity<Map<String, Map<String, List<String>>>> viewMyFamily(@PathVariable Long userId) {
        List<FamilyResponseDTO> familyList = familyService.searchFamily(userId);

        Map<String, Map<String, List<String>>> resultMap = familyList.stream()
                .collect(Collectors.toMap(
                        family -> homeService.getHomeInfo(family.getHomeId()).getName(),
                        family -> getFamilyInfo(family.getHomeId(), userId)
                ));

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }
    
    @DeleteMapping("/home/{homeId}") //집 삭제 DELETE
    public ResponseEntity<String> deleteHome(@PathVariable Long homeId, HttpServletRequest request){
        Long currentUserId = getCurrentUserId();
        homeService.deleteHome(homeId, currentUserId);
        return ResponseEntity.ok().body("집이 삭제되었습니다.");
    }
    @PatchMapping("/home/{homeId}") //집 수정 PATCH
    public ResponseEntity<HomeDto> updateHome(@PathVariable Long homeId, @RequestBody HomeDto homeDto, HttpServletRequest request){
        Long currentUserId = getCurrentUserId();
        HomeDto updatedHome = homeService.updateHome(homeId, homeDto, currentUserId);
        if (updatedHome != null) {
            return ResponseEntity.ok(updatedHome);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(homeDto);
        }
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
//    private Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if ((authentication != null) && (authentication.getPrincipal() instanceof UserDetails)) {
//            String userName = authentication.getName();
//            Long userId = userService.findUserIdByPhone(userName);
//            return userId;
//        }
//        return null; // 인증된 사용자가 없을 경우 null 반환
//    }
}