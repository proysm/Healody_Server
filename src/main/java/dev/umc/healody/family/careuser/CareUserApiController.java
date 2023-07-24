package dev.umc.healody.family.careuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
해야 하는 것
1. 돌봄 계정을 어떻게 식별하여 보여줄게 할 것인가(지금은 그냥 가지고 있는 돌봄만 보여줌,,)
2. 삭제할 때도 어떻게 식별하여 삭제할 것인가
* */

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class CareUserApiController {

    private final CareUserService careUserService;

    //돌봄 추가
    @PostMapping
    public ResponseEntity<CareUserDTO> create(@RequestBody CareUserDTO careUserDTO){

        if(careUserService.checkDuplicate(careUserDTO) ||
                careUserService.getCareUserNumber(careUserDTO.getHome_id()) >= 3){
            return ResponseEntity.notFound().build();
        }

        CareUserDTO careUser = careUserService.create(careUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(careUser);
    }

    @GetMapping("care-user/{id}")
    public ResponseEntity<CareUserDTO> readByCareUserId(@PathVariable Long id){
        Optional<CareUserDTO> careUserDTO = careUserService.findOne(id);

        if(careUserDTO.isPresent()) {
            return ResponseEntity.ok(careUserDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{home_id}")
    public ResponseEntity<List<CareUserDTO>> readByHomeId(@PathVariable Long home_id){
        List<CareUserDTO> careUsers = careUserService.findCareUsers(home_id);

        if(!careUsers.isEmpty()) {
            return ResponseEntity.ok(careUsers);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<CareUserDTO> update(@PathVariable Long id, @RequestBody CareUserDTO careUserDTO){
        CareUserDTO update = careUserService.update(id, careUserDTO);
        return ResponseEntity.ok(update);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<CareUserDTO> careUser = careUserService.findOne(id);

        if (careUser.isPresent()) {
            careUserService.delete(id);
            return ResponseEntity.noContent().build();  // Successfully deleted
        } else {
            return ResponseEntity.notFound().build();  // User not found
        }
    }
}
