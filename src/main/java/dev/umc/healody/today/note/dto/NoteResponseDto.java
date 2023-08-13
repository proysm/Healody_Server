package dev.umc.healody.today.note.dto;

import dev.umc.healody.family.careuser.domain.CareUser;
import dev.umc.healody.family.careuser.domain.CareUserNote;
import dev.umc.healody.today.note.Note;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter @NoArgsConstructor
public class NoteResponseDto {

    Long noteId;  // 이게 있어야지 상세 조회 가능
    String date;
    String title;
    String noteType;

    public NoteResponseDto(Long noteId, String date, String title, String noteType) {
        this.noteId = noteId;
        this.date = date;
        this.title = title;
        this.noteType = noteType;
    }

    public List<NoteResponseDto> toDto(List<Note> notes) {

        NoteResponseDtoList responseDtoList = new NoteResponseDtoList();

        for(Note note : notes) {
            this.noteId = note.getId();

            // Date to String
            Date realDate = note.getDate();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String stringDate = dateFormat.format(realDate);
            this.date = stringDate;

            this.title = note.getTitle();

            // NoteType 한글 적용
            if (note.getNoteType().equals("H")) {
                this.noteType = "병원";
            } else if (note.getNoteType().equals("M")) {
                this.noteType = "약";
            } else if (note.getNoteType().equals("S")) {
                this.noteType = "증상";
            } else {
                System.out.println("noteType 예외처리");
            }

            responseDtoList.addResponseDto(new NoteResponseDto(noteId, date, title, noteType));
        }

        return responseDtoList.getResponseDtoList();
    }

    public List<NoteResponseDto> toDtoCareUser(List<CareUserNote> notes) {

        NoteResponseDtoList responseDtoList = new NoteResponseDtoList();

        for(CareUserNote note : notes) {
            this.noteId = note.getId();

            // Date to String
            Date realDate = note.getDate();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String stringDate = dateFormat.format(realDate);
            this.date = stringDate;

            this.title = note.getTitle();

            // NoteType 한글 적용
            if (note.getNoteType().equals("H")) {
                this.noteType = "병원";
            } else if (note.getNoteType().equals("M")) {
                this.noteType = "약";
            } else if (note.getNoteType().equals("S")) {
                this.noteType = "증상";
            } else {
                System.out.println("noteType 예외처리");
            }

            responseDtoList.addResponseDto(new NoteResponseDto(noteId, date, title, noteType));
        }

        return responseDtoList.getResponseDtoList();
    }
}
