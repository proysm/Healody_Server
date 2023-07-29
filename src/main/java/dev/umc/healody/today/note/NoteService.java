package dev.umc.healody.today.note;

import dev.umc.healody.today.note.dto.*;
import dev.umc.healody.today.note.type.Hospital;
import dev.umc.healody.today.note.type.Medicine;
import dev.umc.healody.today.note.type.Symptom;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createNoteHospital(Long userId, HospitalRequestDto requestDto) {
        // 사용자 아이디를 통해 Entity 조회
        Optional<User> byId = userRepository.findById(userId);
        User user = new User();
        if(byId.isPresent())
            user = byId.get();

        Hospital hospital = requestDto.toEntity(user);
        return noteRepository.save(hospital).getId();
    }

    @Transactional
    public Long createNoteMedicine(Long userId, MedicineRequestDto requestDto) {
        // 사용자 아이디를 통해 Entity 조회
        Optional<User> byId = userRepository.findById(userId);
        User user = new User();
        if(byId.isPresent())
            user = byId.get();

        Medicine medicine = requestDto.toEntity(user);
        return noteRepository.save(medicine).getId();
    }

    @Transactional
    public Long createNoteSymptom(Long userId, SymptomRequestDto requestDto) {
        // 사용자 아이디를 통해 Entity 조회
        Optional<User> byId = userRepository.findById(userId);
        User user = new User();
        if(byId.isPresent())
            user = byId.get();

        Symptom symptom = requestDto.toEntity(user);
        return noteRepository.save(symptom).getId();
    }

    public HospitalResponseDto findNoteHospital(Long noteId) {
        // 기록 아이디를 통해 Entity 조회
        Optional<Note> byId = noteRepository.findById(noteId);
        Hospital hospital = new Hospital();
        if(byId.isPresent())
            hospital = (Hospital) byId.get();

        HospitalResponseDto responseDto = new HospitalResponseDto();
        return responseDto.toDto(hospital);
    }

    public MedicineResponseDto findNoteMedicine(Long noteId) {
        // 기록 아이디를 통해 Entity 조회
        Optional<Note> byId = noteRepository.findById(noteId);
        Medicine medicine = new Medicine();
        if(byId.isPresent())
            medicine = (Medicine) byId.get();

        MedicineResponseDto responseDto = new MedicineResponseDto();
        return responseDto.toDto(medicine);
    }

    public SymptomResponseDto findNoteSymptom(Long noteId) {
        // 기록 아이디를 통해 Entity 조회
        Optional<Note> byId = noteRepository.findById(noteId);
        Symptom symptom = new Symptom();
        if(byId.isPresent())
            symptom = (Symptom) byId.get();

        SymptomResponseDto responseDto = new SymptomResponseDto();
        return responseDto.toDto(symptom);
    }

    @Transactional
    public Long updateNoteHospital(Long userId, Long noteId, HospitalRequestDto requestDto) {
        // 사용자 아이디를 통해 Entity 조회
        Optional<User> byUserId = userRepository.findById(userId);
        User user = new User();
        if(byUserId.isPresent())
            user = byUserId.get();

        // 기록 아이디를 통해 Entity 조회
        Optional<Note> byNoteId = noteRepository.findById(noteId);
        Hospital hospital = new Hospital();
        if(byNoteId.isPresent())
            hospital = (Hospital) byNoteId.get();

        // 변경감지 체크
        // 작성하려는 사용자와 DB에 저장된 사용자와 일치하지 않는 경우 예외처리 진행 가능
        // 현재 진행하고 있는 세션의 사용자 아이디는 필요하니까 일단 가져오자
        if(requestDto.getDate() != null)
            hospital.updateDate(requestDto.getDate());

        if(requestDto.getTitle() != null)
            hospital.updateTitle(requestDto.getTitle());

        if(requestDto.getMemo() != null)
            hospital.updateMemo((requestDto.getMemo()));

        if(requestDto.getPurpose() != null)
            hospital.updatePurpose(requestDto.getPurpose());

        if(requestDto.getName() != null)
            hospital.updateName(requestDto.getName());

        if(requestDto.getSurgery() != null)
            hospital.updateSurgery(requestDto.getSurgery());

        return hospital.getId();
    }

    @Transactional
    public Long updateNoteMedicine(Long userId, Long noteId, MedicineRequestDto requestDto) {
        // 사용자 아이디를 통해 Entity 조회
        Optional<User> byUserId = userRepository.findById(userId);
        User user = new User();
        if(byUserId.isPresent())
            user = byUserId.get();

        // 기록 아이디를 통해 Entity 조회
        Optional<Note> byId = noteRepository.findById(noteId);
        Medicine medicine = new Medicine();
        if(byId.isPresent())
            medicine = (Medicine) byId.get();

        // 변경감지 체크
        if(requestDto.getDate() != null)
            medicine.updateDate(requestDto.getDate());

        if(requestDto.getTitle() != null)
            medicine.updateTitle(requestDto.getTitle());

        if(requestDto.getMemo() != null)
            medicine.updateMemo(requestDto.getMemo());

        if((requestDto.getMedicine1() != null) || (requestDto.getMedicine2() != null) || (requestDto.getMedicine3() != null)) {
            medicine.updateMedicine1(requestDto.getMedicine1());
            medicine.updateMedicine2(requestDto.getMedicine2());
            medicine.updateMedicine3(requestDto.getMedicine3());
        }

        if(requestDto.getPlace() != null) {
            medicine.updatePlace(requestDto.getPlace());
        }

        return medicine.getId();
    }

    @Transactional
    public Long updateNoteSymptom(Long userId, Long noteId, SymptomRequestDto requestDto) {
        // 사용자 아이디를 통해 Entity 조회
        Optional<User> byUserId = userRepository.findById(userId);
        User user = new User();
        if(byUserId.isPresent())
            user = byUserId.get();

        // 기록 아이디를 통해 Entity 조회
        Optional<Note> byId = noteRepository.findById(noteId);
        Symptom symptom = new Symptom();
        if(byId.isPresent())
            symptom = (Symptom) byId.get();

        // 변경감지 체크
        if(requestDto.getDate() != null)
            symptom.updateDate(requestDto.getDate());

        if(requestDto.getTitle() != null)
            symptom.updateTitle(requestDto.getTitle());

        if(requestDto.getMemo() != null)
            symptom.updateMemo(requestDto.getMemo());

        if(requestDto.getName() != null)
            symptom.updateName(requestDto.getName());

        return symptom.getId();
    }

    @Transactional
    public void deleteNoteHospital(Long noteId) {
        // 기록 아이디를 통해 Entity 조회
        Optional<Note> byNoteId = noteRepository.findById(noteId);
        Hospital hospital = new Hospital();
        if(byNoteId.isPresent())
            hospital = (Hospital) byNoteId.get();

        // 찾으려는 기록이 없는 경우 예외처리 진행

        noteRepository.delete(hospital);
    }

    @Transactional
    public void deleteNoteMedicine(Long noteId) {
        // 기록 아이디를 통해 Entity 조회
        Optional<Note> byId = noteRepository.findById(noteId);
        Medicine medicine = new Medicine();
        if(byId.isPresent())
            medicine = (Medicine) byId.get();

        noteRepository.delete(medicine);
    }

    @Transactional
    public void deleteNoteSymptom(Long noteId) {
        // 기록 아이디를 통해 Entity 조회
        Optional<Note> byId = noteRepository.findById(noteId);
        Symptom symptom = new Symptom();
        if(byId.isPresent())
            symptom = (Symptom) byId.get();

        noteRepository.delete(symptom);
    }
}
