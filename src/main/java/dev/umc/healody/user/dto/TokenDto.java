package dev.umc.healody.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    String token;
    Long userId;

    public TokenDto(String token) {
        this.token = token;
    }

    //    private String grantType;
//    private String accessToken;
//    private String refreshToken;
//    private String key;

}
