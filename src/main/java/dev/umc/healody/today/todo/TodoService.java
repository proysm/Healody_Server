package dev.umc.healody.today.todo;

import dev.umc.healody.today.todo.dto.TodoRequestDto;
import dev.umc.healody.today.todo.dto.TodoResponseDto;
import dev.umc.healody.usertest.User;
import dev.umc.healody.usertest.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createTodo(Long userId, TodoRequestDto requestDto) {
        Optional<User> byId = userRepository.findById(userId);
        User user = new User();
        if(byId.isPresent())
            user = byId.get();

        Todo todo = requestDto.toEntity(user);
        return todoRepository.save(todo).getId();
    }

    public TodoResponseDto findTodo(Long todoId) {
        Optional<Todo> byId = todoRepository.findById(todoId);
        Todo todo = new Todo();
        if(byId.isPresent())
            todo = byId.get();

        TodoResponseDto responseDto = new TodoResponseDto();
        return responseDto.toDto(todo);
    }

    @Transactional
    public Long updateTodo(Long userId, Long todoId, TodoRequestDto requestDto) {
        Optional<User> byUserId = userRepository.findById(userId);
        User user = new User();
        if(byUserId.isPresent())
            user = byUserId.get();

        Optional<Todo> byTodoId = todoRepository.findById(todoId);
        Todo todo = new Todo();
        if(byTodoId.isPresent())
            todo = byTodoId.get();

        if(requestDto.getDate() != null)
            todo.updateDate(requestDto.getDate());

        if(requestDto.getContent() != null)
            todo.updateContent(requestDto.getContent());

        return todo.getId();
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        Optional<Todo> byId = todoRepository.findById(todoId);
        Todo todo = new Todo();
        if(byId.isPresent())
            todo = byId.get();

        todoRepository.delete(todo);
    }
}
