package dev.umc.healody.today.dto;

import dev.umc.healody.today.note.type.Symptom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter @NoArgsConstructor
public class NoteSymptomResponseDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String title;
    private String memo;
    private String name;

    @Builder
    public NoteSymptomResponseDto(Date date, String title, String memo, String name) {
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.name = name;
    }

    public NoteSymptomResponseDto toDto(Symptom symptom) {
        return NoteSymptomResponseDto.builder()
                .date(symptom.getDate())
                .title(symptom.getTitle())
                .memo(symptom.getMemo())
                .name(symptom.getName())
                .build();
    }
}
