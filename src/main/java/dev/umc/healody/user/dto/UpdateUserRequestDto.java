package dev.umc.healody.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequestDto {
    private String name;

    private Date birth;

    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    private String gender;

    private String image;

    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
    private String nickname;

    private String message;

}
