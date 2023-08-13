package dev.umc.healody.family.careuser;

import dev.umc.healody.family.careuser.domain.CareUserNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareUserNoteRepository extends JpaRepository<CareUserNote, Long> {

    List<CareUserNote> findAllByCareUser_Id(Long userId);
}
