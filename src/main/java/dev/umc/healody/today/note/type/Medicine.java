package dev.umc.healody.today.note.type;

import dev.umc.healody.today.note.Note;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter @NoArgsConstructor
@SuperBuilder
@Entity
public class Medicine extends Note {

    // 리스트로 넣을지 객체 3개 만들어서 넣을지 고민
    private String medicine1;
    private String medicine2;
    private String medicine3;
    private String place;

    public void updateMedicine1(String medicine1) {
        this.medicine1 = medicine1;
    }

    public void updateMedicine2(String medicine2) {
        this.medicine2 = medicine2;
    }

    public void updateMedicine3(String medicine3) {
        this.medicine3 = medicine3;
    }

    public void updatePlace(String place) {
        this.place = place;
    }
}
