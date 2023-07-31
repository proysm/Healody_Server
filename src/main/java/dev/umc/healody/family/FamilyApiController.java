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
    public ResponseEntity<FamilyDTO> addFamily(@RequestBody FamilyDTORequest familyDTORequest){
        Long userId = userService.findUserIdByPhone(familyDTORequest.getUserPhone());
        FamilyDTO familyDTO = FamilyDTO.builder().userId(userId).homeId(familyDTORequest.getHomeId()).build();

        if(userId == null) return ResponseEntity.notFound().build();

        if(familyService.checkFamilyOver(familyDTO.getUserId()) ||
                familyService.checkFamilyDuplicate(familyDTO.getUserId(), familyDTO.getHomeId())){
            return ResponseEntity.notFound().build();
        }

        familyService.create(familyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(familyDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FamilyDTO>> read(@PathVariable Long userId){
        List<FamilyDTO> list = familyService.findFamily(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody FamilyDTO familyDTO){
        boolean result = familyService.delete(familyDTO.getUserId(), familyDTO.getHomeId());

        if(result) {
            return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.notFound().build();
    }
}
