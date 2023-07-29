package dev.umc.healody.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth")
    private java.sql.Date birth;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "password")
    private String password;

    @Column(name = "image")
    private String image;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "message")
    private String message;

    @Builder.Default
    @Column(name = "family_cnt")
    private Long familyCnt = 0L;


    public void buildUser(long userId, String phone, java.sql.Date birth, String email, String gender,
                          String password, String image, String nickname, String message, long familyCnt){

        setUserId(userId);
        setPhone(phone);
        setBirth(birth);
        setEmail(email);
        setGender(gender);
        setPassword(password);
        setImage(image);
        setNickname(nickname);
        setMessage(message);
        setFamilyCnt(familyCnt);

    }

}
