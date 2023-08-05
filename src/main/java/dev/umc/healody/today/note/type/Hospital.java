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
