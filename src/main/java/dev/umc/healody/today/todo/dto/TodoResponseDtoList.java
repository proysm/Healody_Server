package dev.umc.healody.today.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
public class TodoResponseDtoList {

    List<TodoResponseDto> responseDtoList = new ArrayList<>();

    public void addResponseDto(TodoResponseDto responseDto) {
        responseDtoList.add(responseDto);
    }
}
