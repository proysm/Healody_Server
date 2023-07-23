package dev.umc.healody.family;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository {
    //추가할 때 집이 3개 이상이면 못 하게 해야 함.
    Family save(Family family);
    boolean remove(Long user_id, Long family_id);
    List<Family> findById(Long user_id);
    int getFamilyNumber(Long user_id);
    List<Family> findAll();
    boolean existsByFamily(Long user_id, Long family_id);
}
