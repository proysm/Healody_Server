package dev.umc.healody.today.goal;

import dev.umc.healody.today.goal.type.Behavior;
import dev.umc.healody.usertest.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Entity @Getter
public class Goal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate startDate;
    private LocalDate finishDate;

    @Enumerated(value = EnumType.STRING)
    private Behavior behavior;

    // 목표량은 설정해두고 일부 타입은 null 허용하도록 구현 (그냥 null 받게 구현해도 될듯)
    private String value;

    @Builder
    public Goal(User user, LocalDate startDate, LocalDate finishDate, Behavior behavior, String value) {
        this.user = user;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.behavior = behavior;
        this.value = value;
    }

    public void updateStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void updateFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public void updateBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public void updateValue(String value) {
        this.value = value;
    }
}
