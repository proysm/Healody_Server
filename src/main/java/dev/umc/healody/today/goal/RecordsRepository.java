package dev.umc.healody.today.goal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordsRepository extends JpaRepository<Records, Long> {

    Records findByGoalIdAndDays(Long goadId, Long days);
}
