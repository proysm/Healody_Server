package dev.umc.healody.today.dto;

import dev.umc.healody.today.note.type.Symptom;
import dev.umc.healody.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter @NoArgsConstructor
public class NoteSymptomRequestDto {

    private Long userId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String title;
    private String memo;
    private String name;

    @Builder
    public NoteSymptomRequestDto(Long userId, Date date, String title, String memo, String name) {
        this.userId = userId;
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.name = name;
    }

    public Symptom toEntity(User user) {
        return Symptom.builder()
                .user(user)
                .date(date)
                .title(title)
                .memo(memo)
                .name(name)
                .build();
    }
}
