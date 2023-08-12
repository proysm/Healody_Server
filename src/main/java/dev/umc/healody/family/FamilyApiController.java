package dev.umc.healody.family;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static dev.umc.healody.common.userInfo.getCurrentUserId;

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

        return new SuccessResponse<>(SuccessStatus.SUCCESS, familyService.create(request));
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<List<FamilyResponseDTO>> read(@PathVariable Long userId){
//        List<FamilyResponseDTO> list = familyService.findFamily(userId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(list);
//    }

    @DeleteMapping
    public SuccessResponse<Void> delete(@RequestBody FamilyRequestDTO familyDTORequest){
        boolean result = familyService.delete(getCurrentUserId(), familyDTORequest.getHomeId());
        return new SuccessResponse<>(SuccessStatus.SUCCESS);
    }


}
