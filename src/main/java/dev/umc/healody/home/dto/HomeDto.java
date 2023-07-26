package dev.umc.healody.home.dto;

import dev.umc.healody.home.domain.Home;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeDto {
    public Long homeId;
    public String name;


    public Home toEntity(){
        return Home.builder().name(name).homeId(homeId).build();
    }
}
