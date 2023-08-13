package dev.umc.healody.family.careuser.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Entity @Getter
public class CareUserTodo {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "note_id")
    private Long id;
}
