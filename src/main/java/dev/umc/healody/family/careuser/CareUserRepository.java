package dev.umc.healody.family.careuser;

import dev.umc.healody.family.careuser.domain.CareUser;

import java.util.List;
import java.util.Optional;

//jpa 사용할 레파지토리 만들 때, JpaRepository이거 사용하면 좋을 듯 함
public interface CareUserRepository {
    CareUser save(CareUser careUser);
    boolean update(Long id, CareUser careUser);
    boolean remove(Long id);
    Optional<CareUser> findById(Long id);
    List<CareUser> findByHomeId(Long homeId);
    List<CareUser> findAll();
    boolean existsCareUser(Long homeId, String nickname);
    Long getCareUserNumber(Long homeId);
}
