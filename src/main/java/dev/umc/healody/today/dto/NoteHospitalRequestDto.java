package dev.umc.healody.today.dto;

import dev.umc.healody.today.note.type.Hospital;
import dev.umc.healody.today.note.type.Purpose;
import dev.umc.healody.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter @NoArgsConstructor
public class NoteHospitalRequestDto {

    private Long userId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String title;
    private String memo;
    private Purpose purpose;
    private String name;
    private String surgery;

    @Builder
    public NoteHospitalRequestDto(Long userId, Date date, String title, String memo, Purpose purpose, String name, String surgery) {
        this.userId = userId;
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.purpose = purpose;
        this.name = name;
        this.surgery = surgery;
    }

    public Hospital toEntity(User user) {
        return Hospital.builder()
                .user(user)
                .date(date)
                .title(title)
                .memo(memo)
                .purpose(purpose)
                .name(name)
                .surgery(surgery)
                .build();
    }
}
