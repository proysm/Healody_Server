package dev.umc.healody.today.todo.dto;

import dev.umc.healody.family.careuser.domain.CareUser;
import dev.umc.healody.family.careuser.domain.CareUserTodo;
import dev.umc.healody.today.todo.Todo;
import dev.umc.healody.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter @NoArgsConstructor
public class TodoRequestDto {

    private Long userId;

    private String date;
    private String content;

    @Builder
    public TodoRequestDto(Long userId, String date, String content) {
        this.userId = userId;
        this.date = date;
        this.content = content;
    }

    public Todo toEntity(User user, Date date) {
        return Todo.builder()
                .user(user)
                .date(date)
                .content(content)
                .build();
    }

    public CareUserTodo toEntityCareUser(CareUser user, Date date) {
        return CareUserTodo.builder()
                .careUser(user)
                .date(date)
                .content(content)
                .build();
    }
}
