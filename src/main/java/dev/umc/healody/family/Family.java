package dev.umc.healody.family;

import dev.umc.healody.home.domain.Home;
import dev.umc.healody.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Family", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "home_id"}))
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "family_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;

    @Builder
    public Family(User user, Home home){
        this.user = user;
        this.home = home;
    }
}
