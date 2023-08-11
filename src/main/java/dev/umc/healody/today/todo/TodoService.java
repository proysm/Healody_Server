package dev.umc.healody.today.todo;

import dev.umc.healody.today.todo.dto.TodoRequestDto;
import dev.umc.healody.today.todo.dto.TodoResponseDto;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        Date date = new Date();
        try {
            String requestDtoDate = requestDto.getDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = format.parse(requestDtoDate);
        } catch (ParseException e) {
            System.out.println("예외 처리");
        }

        Todo todo = requestDto.toEntity(user, date);
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
