package dev.umc.healody.today.note.dto;

import dev.umc.healody.today.note.type.Medicine;
import dev.umc.healody.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter @NoArgsConstructor
public class MedicineRequestDto {

    private String date;
    private String title;
    private String memo;
    private String medicine1;
    private String medicine2;
    private String medicine3;
    private String place;

    @Builder
    public MedicineRequestDto(String date, String title, String memo, String medicine1, String medicine2, String medicine3, String place) {
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.medicine1 = medicine1;
        this.medicine2 = medicine2;
        this.medicine3 = medicine3;
        this.place = place;
    }

    public Medicine toEntity(User user, Date date) {
        return Medicine.builder()
                .user(user)
                .date(date)
                .title(title)
                .memo(memo)
                .medicine1(medicine1)
                .medicine2(medicine2)
                .medicine3(medicine3)
                .place(place)
                .noteType("M")
                .build();
    }
}
