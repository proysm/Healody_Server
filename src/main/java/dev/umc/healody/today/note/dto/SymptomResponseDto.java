package dev.umc.healody.today.note.dto;

import dev.umc.healody.today.note.type.Symptom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter @NoArgsConstructor
public class SymptomResponseDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String title;
    private String memo;
    private String name;

    @Builder
    public SymptomResponseDto(Date date, String title, String memo, String name) {
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.name = name;
    }

    public SymptomResponseDto toDto(Symptom symptom) {
        return SymptomResponseDto.builder()
                .date(symptom.getDate())
                .title(symptom.getTitle())
                .memo(symptom.getMemo())
                .name(symptom.getName())
                .build();
    }
}
