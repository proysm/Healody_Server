package dev.umc.healody.family.careuser;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CareUserDTORequest {
    private Long id;
    private Long homeId;
    private String image;
    private String nickname;
}
