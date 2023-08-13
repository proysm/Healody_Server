package dev.umc.healody.family.careuser;

import dev.umc.healody.common.FileUploadUtil;
import dev.umc.healody.family.careuser.domain.CareUser;
import dev.umc.healody.family.careuser.domain.CareUserNote;
import dev.umc.healody.family.careuser.dto.CareUserNoteRequestDto;
import dev.umc.healody.family.careuser.dto.CareUserRequestDTO;
import dev.umc.healody.family.careuser.dto.CareUserResponseDTO;
import dev.umc.healody.home.domain.Home;
import dev.umc.healody.home.repository.HomeRepository;
import dev.umc.healody.today.note.dto.NoteResponseDto;
import dev.umc.healody.today.note.dto.NoteResponseDtoList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareUserService {
    private final CareUserRepository careUserRepository;
    private final HomeRepository homeRepository;
    private final CareUserNoteRepository careUserNoteRepository;
    private final CareUserTodoRepository careUserTodoRepository;

    private final FileUploadUtil fileUploadUtil;

    @Transactional
    public Long create(CareUserRequestDTO requestDTO, MultipartFile image) throws IOException {
        Optional<Home> optionalHome = homeRepository.findById(requestDTO.getHomeId());
        Home home = null;

        //중복, 돌볼계정 수 오버, 집 존재 x
        if(checkDuplicate(requestDTO.getHomeId(), requestDTO.getNickname()) ||
                checkCareUserOver(requestDTO.getHomeId()) ||
                optionalHome.isEmpty()){
            return null;
        }

        // 이미지 URL 생성
        String imgUrl = fileUploadUtil.uploadFile("profile", image);

        home = optionalHome.get();
        CareUser careUser = requestDTO.toEntity(home, imgUrl);
        home.setCaring_cnt(home.getCaring_cnt() + 1);
        CareUser save = careUserRepository.save(careUser);

        return save.getId();
    }

    @Transactional(readOnly = true)
    public Optional<CareUserResponseDTO> findCareUser(Long id){
        Optional<CareUser> optionalCareUser = careUserRepository.findById(id);
        if(optionalCareUser.isEmpty()) return Optional.empty();

        CareUser careUser = optionalCareUser.get();
        CareUserResponseDTO responseDTO = new CareUserResponseDTO();
        return Optional.of(responseDTO.toDTO(careUser));
    }

    @Transactional(readOnly = true)
    public List<CareUserResponseDTO> findCareUsers(Long home_id){
        List<CareUser> careUeres = careUserRepository.findByHomeId(home_id);
        return careUeres.stream()
                .map(careUser -> CareUserResponseDTO.builder()
                        .id(careUser.getId())
                        .message(careUser.getMessage())
                        .homeId(careUser.getHome().getHomeId())
                        .nickname(careUser.getNickname())
                        .image(careUser.getNickname())
                        .build()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CareUserResponseDTO> findAll(){
        List<CareUser> careUsers = careUserRepository.findAll();

        return careUsers.stream()
                .map(careUser -> CareUserResponseDTO.builder()
                        .id(careUser.getId())
                        .message(careUser.getMessage())
                        .homeId(careUser.getHome().getHomeId())
                        .nickname(careUser.getNickname())
                        .image(careUser.getNickname())
                        .build()).collect(Collectors.toList());
    }


    @Transactional
    public boolean update(Long id, CareUserRequestDTO requestDTO){
        Optional<CareUser>careUser = careUserRepository.findById(id);

        if (careUser.isEmpty()) return false;

        return careUserRepository.update(id,
                CareUser.builder()
                .message(requestDTO.getMessage())
                .nickname(requestDTO.getNickname())
                .image(requestDTO.getImage())
                .build());
    }

    @Transactional
    public boolean delete(Long id){
        Optional<CareUser>careUser = careUserRepository.findById(id);
        if (careUser.isEmpty()) return false;

        return careUserRepository.remove(id);
    }

    @Transactional
    public boolean checkDuplicate(Long home_id, String nickname){
        return careUserRepository.existsCareUser(home_id, nickname);
    }

    @Transactional
    public boolean checkCareUserOver(Long userId){
        return careUserRepository.getCareUserNumber(userId) >= 4;
    }

    // 돌봄계정 기록 CRUD
    @Transactional
    public Long createNote(Long careUserId, CareUserNoteRequestDto requestDto) {
        CareUser careUser = careUserRepository.findById(careUserId).get();

        Date date = new Date();
        try {
            String requestDtoDate = requestDto.getDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = format.parse(requestDtoDate);
        } catch (ParseException e) {
            System.out.println("예외 처리");
        }

        CareUserNote note = new CareUserNote();
        System.out.println("requestDto.getHospitalName() = " + requestDto.getHospitalName());
        System.out.println("requestDto.getPlace() = " + requestDto.getPlace());
        System.out.println("requestDto.getSymptomName() = " + requestDto.getSymptomName());

        if (requestDto.getHospitalName() != null) {
            System.out.println("CareUserService.createNote.hospital");
            note = requestDto.toEntityHospital(careUser, date);
        } else if (requestDto.getPlace() != null) {
            System.out.println("CareUserService.createNote.medicine");
            note = requestDto.toEntityMedicine(careUser, date);
        } else if (requestDto.getSymptomName() != null) {
            System.out.println("CareUserService.createNote.symptom");
            note = requestDto.toEntitySymptom(careUser, date);
        } else {
            System.out.println("돌봄계정 note 저장 예외처리");
        }

        return careUserNoteRepository.save(note).getId();
    }

    public List<NoteResponseDto> getNoteByUserId(Long userId) {
        List<CareUserNote> noteList = careUserNoteRepository.findAllByCareUser_Id(userId);
        NoteResponseDto responseDto = new NoteResponseDto();
        return responseDto.toDtoCareUser(noteList);
    }

    // 돌봄계정 할일 CRUD
}
