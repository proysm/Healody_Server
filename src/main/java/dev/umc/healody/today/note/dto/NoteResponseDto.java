package dev.umc.healody.today.note.dto;

import dev.umc.healody.today.note.Note;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @NoArgsConstructor
public class NoteResponseDto {

    Long noteId;  // 이게 있어야지 상세 조회 가능
    Date date;
    String title;
    String noteType;

    List<NoteResponseDto> noteResponseDtoList = new ArrayList<>();

    public NoteResponseDto(Long noteId, Date date, String title, String noteType) {
        this.noteId = noteId;
        this.date = date;
        this.title = title;
        this.noteType = noteType;
    }

    public List<NoteResponseDto> toDto(List<Note> notes) {

        for(Note note : notes) {
            this.noteId = note.getId();
            this.date = note.getDate();
            this.title = note.getTitle();
            if (note.getNoteType() == "H") {
                this.noteType = "병원";
            } else if (note.getNoteType() == "M") {
                this.noteType = "약";
            } else if (note.getNoteType() == "S") {
                this.noteType = "증상";
            } else {
                System.out.println("예외처리");
            }

            noteResponseDtoList.add(new NoteResponseDto(noteId, date, title, noteType));
        }

        return noteResponseDtoList;
    }
}
