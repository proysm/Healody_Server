package dev.umc.healody.family;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/family")
public class FamilyApiController {
    private final FamilyService familyService;

    @PostMapping("/add")
    public ResponseEntity<FamilyDTO> addFamily(@RequestBody FamilyDTORequest familyDTORequest){
        //Long userId = userService.findByPhone(familyDTORequest.getUserPhone());
        //FamilyDTO familyDTO = FamilyDTO.builder().userId(userId).familyId(familyDTORequest.getFamilyId()).build();
        FamilyDTO familyDTO = FamilyDTO.builder().user_id(1L).home_id(familyDTORequest.getHome_id()).build(); //임시

        if(familyService.checkFamilyOver(familyDTO.getUser_id()) ||
                familyService.checkFamilyDuplicate(familyDTO.getUser_id(), familyDTO.getHome_id())){
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
        boolean result = familyService.delete(familyDTO.getUser_id(), familyDTO.getHome_id());

        if(result) {
            System.out.println("FamilyApiController.delete");
            return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.notFound().build();
    }
}
