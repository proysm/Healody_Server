package dev.umc.healody.home.domain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //get 함수 일괄 생성
@Setter //set 함수 일괄 생성
@NoArgsConstructor
@Entity(name = "Home")
public class Home{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //MYSQL에서 AUTO_INCREMENT를 사용하기 때문에 IDENTITY사용
    @Column(nullable = false)
    private Long homeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long user_cnt = 1L;

    @Column(nullable = true)
    private Long caring_cnt;

    @Column(nullable = false)
    private Long admin;

    @Builder
    public Home(String name, Long homeId){
        this.name = name;
        this.homeId = homeId;
    }

}
