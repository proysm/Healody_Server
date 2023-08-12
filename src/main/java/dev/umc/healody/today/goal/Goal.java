package dev.umc.healody.today.goal;

import dev.umc.healody.today.goal.type.Behavior;
import dev.umc.healody.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.*;

@NoArgsConstructor
@Entity @Getter
public class Goal {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "goal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(value = EnumType.STRING)
    private Behavior behavior;

    private String target;
    private Long cnt;

    @Builder
    public Goal(User user, LocalDate startDate, LocalDate endDate, Behavior behavior, String target, Long cnt) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.behavior = behavior;
        this.target = target;
        this.cnt = cnt;
    }

    public void updateStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void updateEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void updateBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public void updateTarget(String target) {
        this.target = target;
    }

    public void plusCnt() {
        this.cnt = this.cnt + 1;
    }
}
