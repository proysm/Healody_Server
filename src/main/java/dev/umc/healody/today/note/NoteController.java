package dev.umc.healody.today.note;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.today.note.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.umc.healody.common.FindUserInfo.getCurrentUserId;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/note/hospital")
    public SuccessResponse<Long> createNoteHospital(@RequestBody HospitalRequestDto requestDto) {
        Long noteId = noteService.createNoteHospital(getCurrentUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.NOTE_CREATE, noteId);
    }

    @PostMapping("/note/medicine")
    public SuccessResponse<Long> createNoteMedicine(@RequestBody MedicineRequestDto requestDto) {
        Long noteId = noteService.createNoteMedicine(getCurrentUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.NOTE_CREATE, noteId);
    }

    @PostMapping("/note/symptom")
    public SuccessResponse<Long> createNoteSymptom(@RequestBody SymptomRequestDto requestDto) {
        Long noteId = noteService.createNoteSymptom(getCurrentUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.NOTE_CREATE, noteId);
    }

    @GetMapping("/note/{userId}")
    public SuccessResponse<List<NoteResponseDto>> getNoteByUserId(@PathVariable Long userId) {
        // 전체 조회할 때는 NoteResponseDto 사용해서 전체 조회 (사용자 아이디 & 날짜 조합)
        // 개별 조회할 때는 각각 ResponseDto 사용해서 개별 조회
        List<NoteResponseDto> responseDtoList = noteService.getNoteByUserId(userId);
        return new SuccessResponse<>(SuccessStatus.NOTE_GET, responseDtoList);
    }

    @GetMapping("/note/hospital/{noteId}")
    public SuccessResponse<HospitalResponseDto> findNoteHospital(@PathVariable Long noteId) {
        HospitalResponseDto responseDto = noteService.findNoteHospital(noteId);
        return new SuccessResponse<>(SuccessStatus.NOTE_GET, responseDto);
    }

    @GetMapping("/note/medicine/{noteId}")
    public SuccessResponse<MedicineResponseDto> findNoteMedicine(@PathVariable Long noteId) {
        MedicineResponseDto responseDto = noteService.findNoteMedicine(noteId);
        return new SuccessResponse<>(SuccessStatus.NOTE_GET, responseDto);
    }

    @GetMapping("/note/symptom/{noteId}")
    public SuccessResponse<SymptomResponseDto> findNoteSymptom(@PathVariable Long noteId) {
        SymptomResponseDto responseDto = noteService.findNoteSymptom(noteId);
        return new SuccessResponse<>(SuccessStatus.NOTE_GET, responseDto);
    }

    @DeleteMapping("/note/hospital/{noteId}")
    public SuccessResponse deleteNoteHospital(@PathVariable Long noteId) {
        noteService.deleteNoteHospital(noteId);
        return new SuccessResponse(SuccessStatus.NOTE_DELETE);
    }

    @DeleteMapping("/note/medicine/{noteId}")
    public SuccessResponse deleteNoteMedicine(@PathVariable Long noteId) {
        noteService.deleteNoteMedicine(noteId);
        return new SuccessResponse(SuccessStatus.NOTE_DELETE);
    }

    @DeleteMapping("/note/symptom/{noteId}")
    public SuccessResponse deleteNoteSymptom(@PathVariable Long noteId) {
        noteService.deleteNoteSymptom(noteId);
        return new SuccessResponse(SuccessStatus.NOTE_DELETE);
    }
}