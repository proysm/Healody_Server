package dev.umc.healody.today.note.type;

import dev.umc.healody.today.note.Note;
import dev.umc.healody.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Getter @NoArgsConstructor
@SuperBuilder
@Entity
public class Hospital extends Note {

    @Enumerated(value = EnumType.STRING)
    private Purpose purpose;
    private String name;
    private String surgery;

    public Hospital(User user, Date date, String title, String memo, Purpose purpose, String name, String surgery) {
        super(user, date, title, memo); // 그럼 여기서 AllArgs 사용하는건가?
        this.purpose = purpose;
        this.name = name;
        this.surgery = surgery;
        System.out.println("Hospital.Hospital");
    }

    public void updatePurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateSurgery(String surgery) {
        this.surgery = surgery;
    }
}
