package dev.umc.healody.today.note.dto;

import dev.umc.healody.today.note.type.Hospital;
import dev.umc.healody.today.note.type.Purpose;
import dev.umc.healody.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter @NoArgsConstructor
public class HospitalRequestDto {

    private String date;
    private String title;
    private String memo;
    private Purpose purpose;
    private String name;
    private String surgery;

    @Builder
    public HospitalRequestDto(String date, String title, String memo, Purpose purpose, String name, String surgery) {
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.purpose = purpose;
        this.name = name;
        this.surgery = surgery;
    }

    public Hospital toEntity(User user, Date date) {
        return Hospital.builder()
                .user(user)
                .date(date)
                .title(title)
                .memo(memo)
                .purpose(purpose)
                .name(name)
                .surgery(surgery)
                .noteType("H")
                .build();
    }
}
