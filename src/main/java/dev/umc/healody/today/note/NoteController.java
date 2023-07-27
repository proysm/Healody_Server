package dev.umc.healody.today.note;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.today.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/note/hospital")
    public SuccessResponse<Long> createNoteHospital(@RequestBody NoteHospitalRequestDto requestDto) {
        Long noteId = noteService.createNoteHospital(requestDto.getUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, noteId);
    }

    @PostMapping("/note/medicine")
    public SuccessResponse<Long> createNoteMedicine(@RequestBody NoteMedicineRequestDto requestDto) {
        Long noteId = noteService.createNoteMedicine(requestDto.getUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, noteId);
    }

    @PostMapping("/note/symptom")
    public SuccessResponse<Long> createNoteSymptom(@RequestBody NoteSymptomRequestDto requestDto) {
        Long noteId = noteService.createNoteSymptom(requestDto.getUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, noteId);
    }

    @GetMapping("/note/hospital/{noteId}")
    public SuccessResponse<NoteHospitalResponseDto> findNoteHospital(@PathVariable Long noteId) {
        NoteHospitalResponseDto responseDto = noteService.findNoteHospital(noteId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, responseDto);
    }

    @GetMapping("/note/medicine/{noteId}")
    public SuccessResponse<NoteMedicineResponseDto> findNoteMedicine(@PathVariable Long noteId) {
        NoteMedicineResponseDto responseDto = noteService.findNoteMedicine(noteId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, responseDto);
    }

    @GetMapping("/note/symptom/{noteId}")
    public SuccessResponse<NoteSymptomResponseDto> findNoteSymptom(@PathVariable Long noteId) {
        NoteSymptomResponseDto responseDto = noteService.findNoteSymptom(noteId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, responseDto);
    }

    @PatchMapping("/note/hospital/{noteId}")
    public SuccessResponse<Long> updateNoteHospital(@PathVariable Long noteId, @RequestBody NoteHospitalRequestDto requestDto) {
        Long updateId = noteService.updateNoteHospital(requestDto.getUserId(), noteId, requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, updateId);
    }

    @PatchMapping("/note/medicine/{noteId}")
    public SuccessResponse<Long> updateNoteMedicine(@PathVariable Long noteId, @RequestBody NoteMedicineRequestDto requestDto) {
        Long updateId = noteService.updateNoteMedicine(requestDto.getUserId(), noteId, requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, updateId);
    }

    @PatchMapping("/note/symptom/{noteId}")
    public SuccessResponse<Long> updateNoteSymptom(@PathVariable Long noteId, @RequestBody NoteSymptomRequestDto requestDto) {
        Long updateId = noteService.updateNoteSymptom(requestDto.getUserId(), noteId, requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, updateId);
    }

    @DeleteMapping("/note/hospital/{noteId}")
    public SuccessResponse deleteNoteHospital(@PathVariable Long noteId) {
        noteService.deleteNoteHospital(noteId);
        return new SuccessResponse(SuccessStatus.SUCCESS);
    }

    @DeleteMapping("/note/medicine/{noteId}")
    public SuccessResponse deleteNoteMedicine(@PathVariable Long noteId) {
        noteService.deleteNoteMedicine(noteId);
        return new SuccessResponse(SuccessStatus.SUCCESS);
    }

    @DeleteMapping("/note/symptom/{noteId}")
    public SuccessResponse deleteNoteSymptom(@PathVariable Long noteId) {
        noteService.deleteNoteSymptom(noteId);
        return new SuccessResponse(SuccessStatus.SUCCESS);
    }
}