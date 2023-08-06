package dev.umc.healody.today.goal;

import dev.umc.healody.today.goal.type.Behavior;
import dev.umc.healody.user.entity.User;
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
    private LocalDate endDate;

    @Enumerated(value = EnumType.STRING)
    private Behavior behavior;

    // 목표량은 설정해두고 일부 타입은 null 허용하도록 구현 (그냥 null 받게 구현해도 될듯)
    private String quantity;

    @Builder
    public Goal(User user, LocalDate startDate, LocalDate endDate, Behavior behavior, String quantity) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.behavior = behavior;
        this.quantity = quantity;
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

    public void updateQuantity(String quantity) {
        this.quantity = quantity;
    }
}
