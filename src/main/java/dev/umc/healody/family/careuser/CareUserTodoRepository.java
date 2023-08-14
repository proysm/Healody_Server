package dev.umc.healody.family.careuser;

import dev.umc.healody.family.careuser.domain.CareUserTodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CareUserTodoRepository extends JpaRepository<CareUserTodo, Long> {

    List<CareUserTodo> findAllByCareUser_IdAndDate(Long userId, Date date);
}
