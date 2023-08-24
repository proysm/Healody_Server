package dev.umc.healody.family.careuser.dto;

import dev.umc.healody.family.careuser.domain.CareUser;
import dev.umc.healody.home.domain.Home;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@NoArgsConstructor
public class CareUserRequestDTO {
    private Long id;
    private Long homeId;
    private String image;
    private String nickname;
    private String message;

    @Builder
    public CareUserRequestDTO(Long homeId, String image, String nickname) {
        this.homeId = homeId;
        this.image = image;
        this.nickname = nickname;
    }

    public CareUser toEntity(Home home, String imgUrl){
        return CareUser.builder().home(home).image(imgUrl).nickname(nickname).message(message).build();
    }
}
