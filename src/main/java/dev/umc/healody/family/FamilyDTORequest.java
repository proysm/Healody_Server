package dev.umc.healody.family;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FamilyDTORequest {
    private String userPhone;
    private Long home_id;
}
