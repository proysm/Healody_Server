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
    private Long user_cnt = 0L;

    @Column(nullable = false)
    private Long caring_cnt = 0L;

    @Column(nullable = false)
    private Long admin;

    @Column(nullable = false)
    private String info;

    @Builder
    public Home(String name, Long homeId, Long admin, String info){
        this.name = name;
        this.homeId = homeId;
        this.admin = admin;
        this.info = info;
    }

}
