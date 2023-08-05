package dev.umc.healody.today.note.type;

import lombok.Getter;

@Getter
public enum Purpose {

    OUTPATIENT("외래"), HOSPITALIZATION("입원"), EMERGENCY("응급");

    private final String displayValue;

    Purpose(String displayValue) {
        this.displayValue = displayValue;
    }
}
