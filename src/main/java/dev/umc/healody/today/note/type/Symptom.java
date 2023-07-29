package dev.umc.healody.today.note.type;

import dev.umc.healody.today.note.Note;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter @NoArgsConstructor
@SuperBuilder
@Entity
public class Symptom extends Note {

    private String name;

    public void updateName(String name) {
        this.name = name;
    }
}
