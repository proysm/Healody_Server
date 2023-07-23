package dev.umc.healody.family;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FamilyDTO {
    private Long user_id;
    private Long family_id;

    public Family toEntity(){
        return Family.builder().user_id(user_id).family_id(family_id).build();
    }
}
