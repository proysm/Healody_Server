package dev.umc.healody.today.todo;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.today.todo.dto.TodoRequestDto;
import dev.umc.healody.today.todo.dto.TodoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/api")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo")
    public SuccessResponse<Long> createTodo(@RequestBody TodoRequestDto requestDto) {
        Long todoId = todoService.createTodo(requestDto.getUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, todoId);
    }

    @GetMapping("/todo/{todoId}")
    public SuccessResponse<TodoResponseDto> findTodo(@PathVariable Long todoId) {
        TodoResponseDto responseDto = todoService.findTodo(todoId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, responseDto);
    }

    @PatchMapping("/todo/{todoId}")
    public SuccessResponse<Long> updateTodo(@PathVariable Long todoId, @RequestBody TodoRequestDto requestDto) {
        Long updateId = todoService.updateTodo(requestDto.getUserId(), todoId, requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, updateId);
    }

    @DeleteMapping("/todo/{todoId}")
    public SuccessResponse deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS);
    }
}
