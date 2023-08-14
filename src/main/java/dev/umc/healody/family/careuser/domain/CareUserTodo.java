package dev.umc.healody.family.careuser.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Entity @Getter
public class CareUserTodo {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "careuser_id")
    private CareUser careUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String content;

    @Builder
    public CareUserTodo(CareUser careUser, Date date, String content) {
        this.careUser = careUser;
        this.date = date;
        this.content = content;
    }

    public void updateDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            this.date = format.parse(date);
        } catch (ParseException e) {
            System.out.println("updateDate 예외 처리");
        }
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
