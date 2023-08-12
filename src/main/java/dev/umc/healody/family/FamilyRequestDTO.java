package dev.umc.healody.family;

import dev.umc.healody.home.domain.Home;
import dev.umc.healody.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FamilyRequestDTO {
    private String userPhone;
    private Long userId;
    private Long homeId;

    @Builder
    public FamilyRequestDTO(Long userId, Long homeId) {
        this.userId = userId;
        this.homeId = homeId;
    }

    public Family toEntity(User user, Home home){
        return Family.builder().user(user).home(home).build();
    }
}
