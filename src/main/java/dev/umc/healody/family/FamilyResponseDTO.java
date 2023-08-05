package dev.umc.healody.family;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class FamilyResponseDTO {

    private Long userId;
    private Long homeId;

    @Builder
    public FamilyResponseDTO(Long userId, Long homeId) {
        this.userId = userId;
        this.homeId = homeId;
    }

    public FamilyResponseDTO toDTO(Family family){
        return FamilyResponseDTO.builder()
                .userId(family.getUser().getUserId())
                .homeId(family.getHome().getHomeId())
                .build();
    }
}
