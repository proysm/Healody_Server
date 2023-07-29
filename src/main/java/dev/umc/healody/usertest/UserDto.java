package dev.umc.healody.usertest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @NoArgsConstructor @Setter
public class UserDto {

    private String name;

    public User toEntity(UserDto userDto) {
        return new User(userDto.getName());
    }
}
