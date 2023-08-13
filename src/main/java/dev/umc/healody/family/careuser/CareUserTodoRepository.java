package dev.umc.healody.family.careuser;

import dev.umc.healody.family.careuser.domain.CareUserTodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareUserTodoRepository extends JpaRepository<CareUserTodo, Long> {
}
