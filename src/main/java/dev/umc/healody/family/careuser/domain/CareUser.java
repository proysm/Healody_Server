package dev.umc.healody.family.careuser.domain;

import dev.umc.healody.home.domain.Home;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "CareUser")
public class CareUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "careuser_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;
    @Column(name = "image")
    private String image;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "message")
    private String message;

    //사실 빌더까지는 필요 없을 것 같은나 나중에 기획적으로 추가된다는 가정하여 작성 했음.
    @Builder
    public CareUser(Home home, String image, String nickname, String message){
        this.home = home;
        this.nickname = nickname;
        this.image = image;
        this.message = message;
    }

    public void update(String nickname, String image, String message){
        this.nickname = nickname;
        this.image = image;
        this.message = message;
    }
}
