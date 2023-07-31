package dev.umc.healody.today.note;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.today.note.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/note/hospital")
    public SuccessResponse<Long> createNoteHospital(@RequestBody HospitalRequestDto requestDto) {
        Long noteId = noteService.createNoteHospital(requestDto.getUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, noteId);
    }

    @PostMapping("/note/medicine")
    public SuccessResponse<Long> createNoteMedicine(@RequestBody MedicineRequestDto requestDto) {
        Long noteId = noteService.createNoteMedicine(requestDto.getUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, noteId);
    }

    @PostMapping("/note/symptom")
    public SuccessResponse<Long> createNoteSymptom(@RequestBody SymptomRequestDto requestDto) {
        Long noteId = noteService.createNoteSymptom(requestDto.getUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, noteId);
    }

    @GetMapping("/note/hospital/{noteId}")
    public SuccessResponse<HospitalResponseDto> findNoteHospital(@PathVariable Long noteId) {
        HospitalResponseDto responseDto = noteService.findNoteHospital(noteId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, responseDto);
    }

    @GetMapping("/note/medicine/{noteId}")
    public SuccessResponse<MedicineResponseDto> findNoteMedicine(@PathVariable Long noteId) {
        MedicineResponseDto responseDto = noteService.findNoteMedicine(noteId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, responseDto);
    }

    @GetMapping("/note/symptom/{noteId}")
    public SuccessResponse<SymptomResponseDto> findNoteSymptom(@PathVariable Long noteId) {
        SymptomResponseDto responseDto = noteService.findNoteSymptom(noteId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, responseDto);
    }

    @PatchMapping("/note/hospital/{noteId}")
    public SuccessResponse<Long> updateNoteHospital(@PathVariable Long noteId, @RequestBody HospitalRequestDto requestDto) {
        Long updateId = noteService.updateNoteHospital(requestDto.getUserId(), noteId, requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, updateId);
    }

    @PatchMapping("/note/medicine/{noteId}")
    public SuccessResponse<Long> updateNoteMedicine(@PathVariable Long noteId, @RequestBody MedicineRequestDto requestDto) {
        Long updateId = noteService.updateNoteMedicine(requestDto.getUserId(), noteId, requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, updateId);
    }

    @PatchMapping("/note/symptom/{noteId}")
    public SuccessResponse<Long> updateNoteSymptom(@PathVariable Long noteId, @RequestBody SymptomRequestDto requestDto) {
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