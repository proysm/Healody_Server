package dev.umc.healody.family.careuser;

import dev.umc.healody.common.FileUploadUtil;
import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.family.careuser.dto.CareUserNoteRequestDto;
import dev.umc.healody.family.careuser.dto.CareUserRequestDTO;
import dev.umc.healody.today.note.dto.NoteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    private final FileUploadUtil fileUploadUtil;

    //돌봄 추가
    @PostMapping
    public SuccessResponse<Long> create(@RequestPart CareUserRequestDTO requestDTO, @RequestPart MultipartFile image) throws IOException {
        Long careUerId = careUserService.create(requestDTO, image);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, careUerId);
    }

    @PutMapping
    public SuccessResponse<Long> update(@RequestBody CareUserRequestDTO careUserRequestDTO){
        boolean result = careUserService.update(careUserRequestDTO.getId(), careUserRequestDTO);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, careUserRequestDTO.getId());
    }


    @DeleteMapping("/{careuserId}")
    public SuccessResponse<Void> delete(@PathVariable Long careuserId) {
        boolean result = careUserService.delete(careuserId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS);
    }

    @PostMapping("/note")
    public SuccessResponse<Long> createNote(@RequestBody CareUserNoteRequestDto requestDto) {
        Long noteId = careUserService.createNote(requestDto.getUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, noteId);
    }

    @GetMapping("/note/{careUserId}")
    public SuccessResponse<List<NoteResponseDto>> getNoteByUserId(@PathVariable Long careUserId) {
        List<NoteResponseDto> responseDtoList = careUserService.getNoteByUserId(careUserId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, responseDtoList);
    }

    @PostMapping("/test/file/upload")
    public String uploadFile(@RequestParam("category") String category, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        return fileUploadUtil.uploadFile(category, multipartFile);
    }

}
