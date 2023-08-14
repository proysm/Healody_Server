package dev.umc.healody.today.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByUser_UserIdAndDate(Long userId, Date date);
}
