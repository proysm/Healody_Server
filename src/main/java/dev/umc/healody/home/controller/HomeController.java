package dev.umc.healody.home.controller;

import dev.umc.healody.home.domain.Home;
import dev.umc.healody.home.dto.HomeDto;
import dev.umc.healody.home.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class HomeController {

    private HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        System.out.println("HomeController.HomeController");
        this.homeService = homeService;
    }

    @PostMapping("/home") //집 추가 POST
    public ResponseEntity<HomeDto> createHome(@RequestBody HomeDto homeDto, HttpServletRequest request){
        Long adminId = homeService.getCurrentUserId(request);
        HomeDto newHome = homeService.createHome(homeDto, adminId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHome);
    }
    //집을 만들며 그 안에 가족을 넣기, 그리고 user_cnt 관리
    //돌봄계정  careuser_cnt 관리
    //가족구성원의 할일 목표 등등 정보 조회하기
    @GetMapping("/home/{homeId}") //집 조회 GET
    public ResponseEntity<HomeDto> getHomeInfo(@PathVariable Long homeId){
        HomeDto homeDto = homeService.getHomeInfo(homeId);
        return ResponseEntity.status(HttpStatus.OK).body(homeDto);
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
}
