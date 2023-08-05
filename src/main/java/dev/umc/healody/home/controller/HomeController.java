package dev.umc.healody.home.controller;

import dev.umc.healody.family.FamilyRequestDTO;
import dev.umc.healody.family.FamilyResponseDTO;
import dev.umc.healody.family.FamilyService;
import dev.umc.healody.family.careuser.CareUserResponseDTO;
import dev.umc.healody.family.careuser.CareUserService;
import dev.umc.healody.home.domain.Home;
import dev.umc.healody.home.dto.HomeDto;
import dev.umc.healody.home.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;
    private final FamilyService familyService;
    private final UserService userService;
    private final CareUserService careUserService;

    @PostMapping("/home") //집 추가 POST
    public ResponseEntity<HomeDto> createHome(@RequestBody HomeDto homeDto, HttpServletRequest request){
        Long adminId = homeService.getCurrentUserId(request);
        HomeDto newHome = homeService.createHome(homeDto, adminId);
        familyService.create(FamilyRequestDTO.builder().userId(adminId).homeId(newHome.homeId).build());
        return ResponseEntity.status(HttpStatus.CREATED).body(newHome);
    }
    //집을 만들며 그 안에 가족을 넣기, 그리고 user_cnt 관리
    //돌봄계정  careuser_cnt 관리
    //가족구성원의 할일 목표 등등 정보 조회하기
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
        HomeDto currentHome = homeService.getHomeInfo(homeId);
        if (homeService.isAdmin(request, currentHome)) {
            homeService.deleteHome(homeId);
            return ResponseEntity.status(HttpStatus.OK).body("집이 삭제되었습니다.");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("관리자 권한이 없습니다.");
        }
    }
    @PatchMapping("/home/{homeId}") //집 수정 PATCH
    public ResponseEntity<HomeDto> updateHome(@PathVariable Long homeId, @RequestBody HomeDto homeDto, HttpServletRequest request){
        HomeDto currentHome = homeService.getHomeInfo(homeId);
        if (homeService.isAdmin(request, currentHome)) {
            HomeDto updatedHome = homeService.updateHome(homeId, homeDto);
            return ResponseEntity.ok(updatedHome);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(homeDto);
        }
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("dhodkseho");
        return "Hello World!";
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