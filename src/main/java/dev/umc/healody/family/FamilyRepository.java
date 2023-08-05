package dev.umc.healody.family;

import java.util.List;

public interface FamilyRepository {
    //추가할 때 집이 3개 이상이면 못 하게 해야 함.
    Family save(Family family);
    boolean remove(Long userId, Long homeId);
    List<Family> findByUserId(Long userId);
    List<Family> findByHomeId(Long homeId);
    int getFamilyNumber(Long userId);
    List<Family> findAll();
    boolean existsByFamily(Long userId, Long homeId);
}
