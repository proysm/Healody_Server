package dev.umc.healody.family.careuser;

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

    //lombok를 통해 ManyToOne 사용하는 방식도 가능한 것 같은데
    //나중에 리랙토링 해도 좋을 듯 함.

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;
    @Column(name = "image")
    private String image;
    @Column(name = "nickname")
    private String nickname;

    //사실 빌더까지는 필요 없을 것 같은나 나중에 기획적으로 추가된다는 가정하여 작성 했음.
    //메모리 레파지토리 테스트 위해 id까지 받음
    @Builder
    public CareUser(Home home, String image, String nickname){
        this.home = home;
        this.nickname = nickname;
        this.image = image;
    }

    public void update(String nickname, String image){
        this.nickname = nickname;
        this.image = image;
    }

    //테스트용 임시 메서드
    public void setId(Long id) {
        this.id = id;
    }
}
