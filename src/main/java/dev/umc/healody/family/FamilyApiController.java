package dev.umc.healody.family;

import com.amazonaws.services.ec2.model.AvailabilityZoneOptInStatus;
import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.user.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import static dev.umc.healody.common.FindUserInfo.getCurrentUserId;
import static dev.umc.healody.common.FindUserInfo.setUserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/family")
public class FamilyApiController {
    private final FamilyService familyService;
    private final UserService userService;

    @PostMapping("/add")
    public SuccessResponse<Long> addFamily(@RequestBody FamilyRequestDTO familyDTORequest){
        Long userId = userService.findUserIdByPhone(familyDTORequest.getUserPhone());
        FamilyRequestDTO request = familyDTORequest.builder().userId(userId).homeId(familyDTORequest.getHomeId()).build();

        return new SuccessResponse<>(SuccessStatus.FAMILY_CREATE, familyService.create(request));
    }

    @PostMapping("/update/{changeHomeId}")
    public SuccessResponse<Long> updateFamily(@RequestBody FamilyRequestDTO familyDTORequest, @PathVariable Long changeHomeId){
        setUserService(userService);
        Long currentUserId = getCurrentUserId();
        boolean isAdmin = familyService.isAdmin(currentUserId, familyDTORequest.getHomeId());
        if(isAdmin) {
            boolean result = familyService.update(familyDTORequest.getUserId(), familyDTORequest.getHomeId(), changeHomeId);
            return new SuccessResponse<>(SuccessStatus.FAMILY_CHANGE);
        }
        return new SuccessResponse<>(SuccessStatus.FORBIDDEN);
    }

    @DeleteMapping("/delete/{homeId}/{userId}") //집 관리자가 가족 구성원을 내보내는 것
    public SuccessResponse<Void> delete(@PathVariable Long homeId, @PathVariable Long userId){
        setUserService(userService);
        Long currentUserId = getCurrentUserId();
        boolean isAdmin = familyService.isAdmin(currentUserId, homeId);
        if(isAdmin) {
        boolean result = familyService.delete(userId, homeId);
        return new SuccessResponse<>(SuccessStatus.FAMILY_DELETE);
        }
        return new SuccessResponse<>(SuccessStatus.FORBIDDEN);
    }

    @DeleteMapping("/exit/{homeId}") //가족 구성원이 스스로 집에서 나가는 것
    public SuccessResponse<Void> exit(@PathVariable Long homeId) {
        setUserService(userService);
        Long currentUserId = getCurrentUserId();
        boolean result = familyService.delete(currentUserId, homeId);
        return new SuccessResponse<>(SuccessStatus.FAMILY_EXIT);
    }


}
