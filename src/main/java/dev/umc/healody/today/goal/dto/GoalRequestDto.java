package dev.umc.healody.today.goal.dto;

import dev.umc.healody.today.goal.Goal;
import dev.umc.healody.today.goal.type.Behavior;
import dev.umc.healody.usertest.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @NoArgsConstructor
public class GoalRequestDto {

    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishDate;

    private Behavior behavior;
    private String value;

    public Goal toEntity(User user) {
        return Goal.builder()
                .user(user)
                .startDate(startDate)
                .finishDate(finishDate)
                .behavior(behavior)
                .value(value)
                .build();
    }
}
