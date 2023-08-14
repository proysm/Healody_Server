package dev.umc.healody.today.todo.dto;

import dev.umc.healody.family.careuser.domain.CareUserTodo;
import dev.umc.healody.today.todo.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter @NoArgsConstructor
public class TodoResponseDto {

    private String date;
    private String content;

    @Builder
    public TodoResponseDto(String date, String content) {
        this.date = date;
        this.content = content;
    }

    public List<TodoResponseDto> toDto(List<Todo> todos) {

        TodoResponseDtoList responseDtoList = new TodoResponseDtoList();

        for(Todo todo : todos) {
            // Date to String
            Date realDate = todo.getDate();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String stringDate = dateFormat.format(realDate);
            this.date = stringDate;

            this.content = todo.getContent();

            responseDtoList.addResponseDto(new TodoResponseDto(date, content));
        }

        return responseDtoList.getResponseDtoList();
    }

    public List<TodoResponseDto> toDtoCareUser(List<CareUserTodo> todos) {

        TodoResponseDtoList responseDtoList = new TodoResponseDtoList();

        for(CareUserTodo todo : todos) {
            // Date to String
            Date realDate = todo.getDate();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String stringDate = dateFormat.format(realDate);
            this.date = stringDate;

            this.content = todo.getContent();

            responseDtoList.addResponseDto(new TodoResponseDto(date, content));
        }

        return responseDtoList.getResponseDtoList();
    }
}
