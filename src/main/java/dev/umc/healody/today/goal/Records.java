package dev.umc.healody.today.goal;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Entity @Getter
public class Records {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "records_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    private LocalDate today;
    private String val;

    @Builder
    public Records(Goal goal, LocalDate today, String val) {
        this.goal = goal;
        this.today = today;
        this.val = val;
    }

    public void updateVal(String val) {
        this.val = val;
    }
}
