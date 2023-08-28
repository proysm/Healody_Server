package dev.umc.healody.today.todo;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.today.todo.dto.TodoRequestDto;
import dev.umc.healody.today.todo.dto.TodoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.umc.healody.common.FindUserInfo.getCurrentUserId;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo")
    public SuccessResponse<Long> createTodo(@RequestBody TodoRequestDto requestDto) {
        Long todoId = todoService.createTodo(getCurrentUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.TODO_CREATE, todoId);
    }

    @GetMapping("/todo/{userId}")
    public SuccessResponse<List<TodoResponseDto>> findTodo(@PathVariable Long userId) {
        List<TodoResponseDto> responseDtoList = todoService.findTodayTodo(userId);
        return new SuccessResponse<>(SuccessStatus.TODO_GET, responseDtoList);
    }

    @PatchMapping("/todo/{todoId}")
    public SuccessResponse<Long> updateTodo(@PathVariable Long todoId, @RequestBody TodoRequestDto requestDto) {
        Long updateId = todoService.updateTodo(getCurrentUserId(), todoId, requestDto);
        return new SuccessResponse<>(SuccessStatus.TODO_UPDATE, updateId);
    }

    @DeleteMapping("/todo/{todoId}")
    public SuccessResponse deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return new SuccessResponse<>(SuccessStatus.TODO_DELETE);
    }
}
