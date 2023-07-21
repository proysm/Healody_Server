package dev.umc.healody.family.careuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home/care-user")
public class CareUserApiController {

    private final CareUserService careUserService;

    //돌봄 추가
    @PostMapping
    public ResponseEntity<CareUserDTO> create(@RequestBody CareUserDTO careUserDTO){
        CareUserDTO careUser = careUserService.create(careUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(careUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareUserDTO> read(@PathVariable Long id){
        Optional<CareUserDTO> careUserDTO = careUserService.findOne(id);

        if(careUserDTO.isPresent()) {
            return ResponseEntity.ok(careUserDTO.get());
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
