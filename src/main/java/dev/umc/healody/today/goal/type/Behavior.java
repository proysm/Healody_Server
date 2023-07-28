package dev.umc.healody.today.goal.type;

import lombok.Getter;

@Getter
public enum Behavior {

    DRINK_WATER("물마시기"), ABSTAIN_DRINK("금주하기"),
    QUIT_SMOKING("금연하기"), DO_EXERCISE("운동하기");

    private final String displayValue;

    Behavior(String displayValue) {
        this.displayValue = displayValue;
    }
}
