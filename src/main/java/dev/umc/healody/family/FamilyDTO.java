package dev.umc.healody.family;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FamilyDTO {
    private Long user_id;
    private Long home_id;

    public Family toEntity(){
        return Family.builder().user_id(user_id).home_id(home_id).build();
    }
}
