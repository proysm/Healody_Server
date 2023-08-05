package dev.umc.healody.today.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query(value = "SELECT * FROM NOTE WHERE USER = :user", nativeQuery = true)
    List<Note> findAllById(@Param("user") Long userId);
}
