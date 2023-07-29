package dev.umc.healody.family.careuser;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CareUserDTO {
    private Long id;
    private Long home_id;
    private String image;
    private String nickname;

    public CareUser toEntity(){
        return CareUser.builder().home_id(home_id).image(image).nickname(nickname).build();
    }
}
