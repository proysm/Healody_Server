package dev.umc.healody.family.careuser;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CareUserDTO {
    private Long homeId;
    private String image;
    private String nickname;

    public CareUser toEntity(){
        return CareUser.builder().homeId(homeId).filename(image).nickname(nickname).build();
    }
}
