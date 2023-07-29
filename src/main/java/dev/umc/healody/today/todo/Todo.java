package dev.umc.healody.today.todo;

import dev.umc.healody.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@Entity @Getter
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String content;

    @Builder
    public Todo(User user, Date date, String content) {
        this.user = user;
        this.date = date;
        this.content = content;
    }

    public void updateDate(Date date) {
        this.date = date;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
