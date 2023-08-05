package dev.umc.healody.family.careuser;

import dev.umc.healody.home.domain.Home;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CareUserResponseDTO {
    private Long id;
    private Long homeId;
    private String image;
    private String nickname;
    private String massage;

    @Builder
    public CareUserResponseDTO(Long id, Long homeId, String image, String nickname, String massage) {
        this.id = id;
        this.homeId = homeId;
        this.image = image;
        this.nickname = nickname;
        this.massage = massage;
    }

    public CareUserResponseDTO toDTO(CareUser careUser){
        return CareUserResponseDTO.builder()
                .id(careUser.getId())
                .massage(careUser.getMassage())
                .homeId(careUser.getHome().getHomeId())
                .image(careUser.getImage())
                .nickname(careUser.getNickname())
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", nickname='" + nickname + '\'' +
                ", massage='" + massage + '\'' +
                '}';
    }
}
