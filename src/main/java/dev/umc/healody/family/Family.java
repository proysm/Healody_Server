package dev.umc.healody.family;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Family", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "family_id"}))
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_family_id")
    private Long id;

    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "family_id")
    private Long family_id;

    @Builder
    public Family(Long user_id, Long family_id){
        this.user_id = user_id;
        this.family_id = family_id;
    }

    //테스트용 임시 메서드
    public void setId(Long id) {
        this.id = id;
    }
}
