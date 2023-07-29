package dev.umc.healody.family.careuser;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CareUserDTORequest {
    private Long home_id;
    private String image;
    private String nickname;

    CareUserDTO toEntity(){
        return CareUserDTO.builder().home_id(home_id).nickname(nickname).image(image).build();
    }
}
