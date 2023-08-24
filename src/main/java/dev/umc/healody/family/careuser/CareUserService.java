package dev.umc.healody.family.careuser;

import dev.umc.healody.family.careuser.domain.CareUserTodo;
import dev.umc.healody.today.todo.Todo;
import dev.umc.healody.today.todo.dto.TodoRequestDto;
import dev.umc.healody.today.todo.dto.TodoResponseDto;
import dev.umc.healody.utils.FileUploadUtil;
import dev.umc.healody.family.careuser.domain.CareUser;
import dev.umc.healody.family.careuser.domain.CareUserNote;
import dev.umc.healody.family.careuser.dto.CareUserNoteRequestDto;
import dev.umc.healody.family.careuser.dto.CareUserRequestDTO;
import dev.umc.healody.family.careuser.dto.CareUserResponseDTO;
import dev.umc.healody.home.domain.Home;
import dev.umc.healody.home.repository.HomeRepository;
import dev.umc.healody.today.note.dto.NoteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
    public Long create(CareUserRequestDTO requestDTO) throws IOException {
        Optional<Home> optionalHome = homeRepository.findById(requestDTO.getHomeId());
        Home home = null;

        //중복, 돌볼계정 수 오버, 집 존재 x
        if(checkDuplicate(requestDTO.getHomeId(), requestDTO.getNickname()) ||
                checkCareUserOver(requestDTO.getHomeId()) ||
                optionalHome.isEmpty()){
            return null;
        }

        // 이미지 URL 생성
        String imgUrl = fileUploadUtil.uploadFile("profile", requestDTO.getImageFile());

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
    public boolean update(Long id, CareUserRequestDTO requestDTO, MultipartFile image) throws IOException {
        Optional<CareUser>careUser = careUserRepository.findById(id);
        String imgUrl = "";
        if (careUser.isEmpty()) return false;

        if(image != null) imgUrl = fileUploadUtil.uploadFile("profile", image);

        return careUserRepository.update(id,
                CareUser.builder()
                .message(requestDTO.getMessage())
                .nickname(requestDTO.getNickname())
                .image(imgUrl)
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

    @Transactional
    public void deleteNote(Long noteId) {
        CareUserNote careUserNote = careUserNoteRepository.findById(noteId).get();
        careUserNoteRepository.delete(careUserNote);
    }

    // 돌봄계정 할일 CRUD
    @Transactional
    public Long createTodo(Long careUserId, TodoRequestDto requestDto) {
        CareUser careUser = careUserRepository.findById(careUserId).get();

        Date date = new Date();
        try {
            String requestDtoDate = requestDto.getDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(requestDtoDate);
        } catch (ParseException e) {
            System.out.println("예외 처리");
        }

        CareUserTodo careUserTodo = requestDto.toEntityCareUser(careUser, date);
        return careUserTodoRepository.save(careUserTodo).getId();
    }

    public List<TodoResponseDto> findTodayTodo(Long userId) {
        // LocalDate to Date
        LocalDate localDate = LocalDate.now();
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        List<CareUserTodo> careUserTodoList = careUserTodoRepository.findAllByCareUser_IdAndDate(userId, date);

        TodoResponseDto responseDto = new TodoResponseDto();
        return responseDto.toDtoCareUser(careUserTodoList);
    }

    @Transactional
    public Long updateTodo(Long userId, Long todoId, TodoRequestDto requestDto) {
        CareUser careUser = careUserRepository.findById(userId).get();
        CareUserTodo careUserTodo = careUserTodoRepository.findById(todoId).get();

        if(requestDto.getDate() != null)
            careUserTodo.updateDate(requestDto.getDate());

        if(requestDto.getContent() != null)
            careUserTodo.updateContent(requestDto.getContent());

        return careUserTodo.getId();
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        CareUserTodo careUserTodo = careUserTodoRepository.findById(todoId).get();
        careUserTodoRepository.delete(careUserTodo);
    }
}
