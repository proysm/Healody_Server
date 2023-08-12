package dev.umc.healody.today.goal.dto;

import dev.umc.healody.today.goal.Goal;
import dev.umc.healody.today.goal.type.Behavior;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @NoArgsConstructor
public class GoalResponseDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishDate;

    private Behavior behavior;
    private String target;

    @Builder
    public GoalResponseDto(LocalDate startDate, LocalDate finishDate, Behavior behavior, String target) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.behavior = behavior;
        this.target = target;
    }

    public GoalResponseDto toDto(Goal goal) {
        return GoalResponseDto.builder()
                .startDate(goal.getStartDate())
                .finishDate(goal.getEndDate())
                .behavior(goal.getBehavior())
                .target(goal.getTarget())
                .build();
    }
}
