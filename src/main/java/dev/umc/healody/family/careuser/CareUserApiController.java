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
@RequestMapping("/api/care-user")
public class CareUserApiController {

    private final CareUserService careUserService;

    //돌봄 추가
    @PostMapping
    public ResponseEntity<CareUserDTO> create(@RequestBody CareUserDTORequest careUserDTORequest){
        CareUserDTO careUserDTO = CareUserDTO.builder()
                .homeId(careUserDTORequest.getHomeId())
                .nickname(careUserDTORequest.getNickname())
                .image(careUserDTORequest.getImage())
                .build();

        if(careUserService.checkDuplicate(careUserDTO.getHomeId(), careUserDTO.getNickname()) ||
                careUserService.checkCareUserOver(careUserDTO.getHomeId())){
            return ResponseEntity.notFound().build();
        }

        CareUserDTO careUser = careUserService.create(careUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(careUser);
    }

    @GetMapping("/{homeId}")
    public ResponseEntity<List<CareUserDTO>> readByHomeId(@PathVariable Long homeId){
        List<CareUserDTO> careUsers = careUserService.findCareUsers(homeId);

        if(!careUsers.isEmpty()) {
            return ResponseEntity.ok(careUsers);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping
    public ResponseEntity<CareUserDTO> update(@RequestBody CareUserDTORequest careUserDTORequest){
        CareUserDTO careUserDTO = CareUserDTO.builder()
                .nickname(careUserDTORequest.getNickname())
                .image(careUserDTORequest.getImage())
                .build();

        CareUserDTO update = careUserService.update(careUserDTORequest.getId(), careUserDTO);
        return ResponseEntity.ok(update);
    }


    @DeleteMapping("/{careuserId}")
    public ResponseEntity<Void> delete(@PathVariable Long careuserId) {
        Optional<CareUserDTO> careUser = careUserService.findOne(careuserId);

        if (careUser.isPresent()) {
            careUserService.delete(careuserId);
            return ResponseEntity.noContent().build();  // Successfully deleted
        } else {
            return ResponseEntity.notFound().build();  // User not found
        }
    }
}
