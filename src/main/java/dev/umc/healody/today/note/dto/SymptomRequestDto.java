package dev.umc.healody.today.note.dto;

import dev.umc.healody.today.note.type.Symptom;
import dev.umc.healody.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter @NoArgsConstructor
public class SymptomRequestDto {

    private String date;
    private String title;
    private String memo;
    private String name;

    @Builder
    public SymptomRequestDto(String date, String title, String memo, String name) {
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.name = name;
    }

    public Symptom toEntity(User user, Date date) {
        return Symptom.builder()
                .user(user)
                .date(date)
                .title(title)
                .memo(memo)
                .name(name)
                .noteType("S")
                .build();
    }
}
