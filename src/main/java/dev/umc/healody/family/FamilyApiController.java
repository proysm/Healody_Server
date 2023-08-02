package dev.umc.healody.family;

import dev.umc.healody.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/family")
public class FamilyApiController {
    private final FamilyService familyService;
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Long> addFamily(@RequestBody FamilyRequestDTO familyDTORequest){
        Long userId = userService.findUserIdByPhone(familyDTORequest.getUserPhone());
        FamilyRequestDTO request = familyDTORequest.builder().userId(userId).homeId(familyDTORequest.getHomeId()).build();

        if(userId == null) return ResponseEntity.notFound().build();

        if(familyService.checkFamilyOver(request.getUserId()) ||
                familyService.checkFamilyDuplicate(request.getUserId(), request.getHomeId())){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(familyService.create(request));
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<List<FamilyResponseDTO>> read(@PathVariable Long userId){
//        List<FamilyResponseDTO> list = familyService.findFamily(userId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(list);
//    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody FamilyRequestDTO familyDTORequest){
        boolean result = familyService.delete(familyDTORequest.getUserId(), familyDTORequest.getHomeId());

        if(result) {
            return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.notFound().build();
    }
}
