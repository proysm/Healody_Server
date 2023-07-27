package dev.umc.healody.today.todo.dto;

import dev.umc.healody.today.todo.Todo;
import dev.umc.healody.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static java.lang.Boolean.*;

@Getter @NoArgsConstructor
public class TodoRequestDto {

    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String content;

    @Builder
    public TodoRequestDto(Long userId, Date date, String content) {
        this.userId = userId;
        this.date = date;
        this.content = content;
    }

    public Todo toEntity(User user) {
        return Todo.builder()
                .user(user)
                .date(date)
                .content(content)
                .build();
    }
}
