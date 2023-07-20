package dev.umc.healody.family.careuser;

import java.util.List;
import java.util.Optional;

//jpa 사용할 레파지토리 만들 때, JpaRepository이거 사용하면 좋을 듯 함
public interface CareUserRepository {
    CareUser save(CareUser careUser);
    boolean update(Long id, CareUser careUser);
    boolean remove(Long id);
    Optional<CareUser> findById(Long id);
    List<CareUser> findAll();
}
