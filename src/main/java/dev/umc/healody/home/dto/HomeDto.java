package dev.umc.healody.home.dto;

import dev.umc.healody.home.domain.Home;
import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeDto {
    public Long homeId;
    public String name;
    public Long admin;


    public Home toEntity(){
        return Home.builder().name(name).homeId(homeId).build();
    }
}
