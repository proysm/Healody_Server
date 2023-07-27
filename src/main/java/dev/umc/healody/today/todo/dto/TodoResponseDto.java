package dev.umc.healody.today.todo.dto;

import dev.umc.healody.today.todo.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter @NoArgsConstructor
public class TodoResponseDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String content;

    @Builder
    public TodoResponseDto(Date date, String content) {
        this.date = date;
        this.content = content;
    }

    public TodoResponseDto toDto(Todo todo) {
        return TodoResponseDto.builder()
                .date(todo.getDate())
                .content(todo.getContent())
                .build();
    }
}
