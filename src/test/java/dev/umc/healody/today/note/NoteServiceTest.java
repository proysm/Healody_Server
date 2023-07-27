package dev.umc.healody.today.note;

import dev.umc.healody.today.dto.NoteHospitalRequestDto;
import dev.umc.healody.today.note.type.Hospital;
import dev.umc.healody.today.note.type.Purpose;
import dev.umc.healody.user.User;
import dev.umc.healody.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

//    @InjectMocks
//    private NoteService noteService;
//
//    @Mock
//    private NoteRepository noteRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Test
//    void 병원기록_작성() {
//        // given
//        User user = new User("jaeyoung");
//        Mockito.when(userRepository.save(user)).thenReturn(user);
//        User save = userRepository.save(user);
//        System.out.println("ServiceTest user = " + user);
//        System.out.println("saveId = " + save.getId());
//        System.out.println("saveName = " + save.getName());
//
//        Date date = new Date(2023, 07, 01);
//
//        NoteHospitalRequestDto requestDto = NoteHospitalRequestDto.builder()
//                .userId(user.getId())
//                .date(date)
//                .title("병원 방문")
//                .memo("메모 테스트")
//                .purpose(Purpose.EMERGENCY)
//                .name("아주대학교")
//                .surgery("양악수술")
//                .build();
//
//        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
////        Mockito.when(noteRepository.save(requestDto.toEntity(user))).thenReturn(new Hospital());
//        Mockito.when(noteRepository.save(any())).thenReturn(new Hospital());
//
//        // when
//        noteService.createNote(user.getId(), requestDto);
//
//        // then
//    }
//
//    void 병원기록_조회() {
//        // given
//        User user = new User("jaeyoung");
//
//        Mockito.when(userRepository.save(user)).thenReturn(user);
//        userRepository.save(user);
//
//        Date date = new Date(2023, 07, 01);
//        NoteHospitalRequestDto requestDto = NoteHospitalRequestDto.builder()
//                .userId(user.getId())
//                .date(date)
//                .title("병원 방문")
//                .memo("메모 테스트")
//                .purpose(Purpose.EMERGENCY)
//                .name("아주대학교")
//                .surgery("양악수술")
//                .build();
//
//        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//        Mockito.when(noteRepository.save(any())).thenReturn(new Hospital());
//        noteService.createNote(user.getId(), requestDto);
//
//        // when
//
//        // then
//    }
}