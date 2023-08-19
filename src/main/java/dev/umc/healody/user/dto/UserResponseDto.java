package dev.umc.healody.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private String name;

    private String phone;

    private Date birth;

    private String email;

    private String gender;

    private String image;

    private String nickname;

    private String message;

    private Long familyCnt;

}
