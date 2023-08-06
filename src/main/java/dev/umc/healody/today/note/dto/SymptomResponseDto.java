package dev.umc.healody.today.note.dto;

import dev.umc.healody.today.note.type.Symptom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @NoArgsConstructor
public class SymptomResponseDto {

    private String date;
    private String title;
    private String memo;
    private String name;
    private String noteType;

    @Builder
    public SymptomResponseDto(String date, String title, String memo, String name, String noteType) {
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.name = name;
        this.noteType = noteType;
    }

    public SymptomResponseDto toDto(Symptom symptom) {

        Date realDate = symptom.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = dateFormat.format(realDate);

        return SymptomResponseDto.builder()
                .date(stringDate)
                .title(symptom.getTitle())
                .memo(symptom.getMemo())
                .name(symptom.getName())
                .noteType(symptom.getNoteType())
                .build();
    }
}
