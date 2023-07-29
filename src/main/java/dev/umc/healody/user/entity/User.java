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
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth")
    private String birth;

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

}
