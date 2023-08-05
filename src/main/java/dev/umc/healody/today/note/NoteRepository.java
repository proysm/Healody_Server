package dev.umc.healody.today.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query(value = "SELECT * FROM NOTE WHERE user_id = :userId", nativeQuery = true)
    List<Note> findAllByUserId(@Param("userId") Long userId);
}
