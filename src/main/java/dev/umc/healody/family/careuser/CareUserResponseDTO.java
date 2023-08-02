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

    @Builder
    public CareUserResponseDTO(Long id, Long homeId, String image, String nickname) {
        this.id = id;
        this.homeId = homeId;
        this.image = image;
        this.nickname = nickname;
    }

    public CareUserResponseDTO toDTO(CareUser careUser){
        return CareUserResponseDTO.builder()
                .id(careUser.getId())
                .homeId(careUser.getHome().getHomeId())
                .image(careUser.getImage())
                .nickname(careUser.getNickname())
                .build();
    }
}
