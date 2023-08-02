package dev.umc.healody.family.careuser;

import dev.umc.healody.home.domain.Home;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CareUserRequestDTO {
    private Long id;
    private Long homeId;
    private String image;
    private String nickname;

    public CareUser toEntity(Home home){
        return CareUser.builder().home(home).image(image).nickname(nickname).build();
    }
}
