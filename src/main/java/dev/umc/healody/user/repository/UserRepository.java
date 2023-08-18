package dev.umc.healody.user.repository;

import dev.umc.healody.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //  JPA는 existsByXXX을 제공하여 해당 값이 존재하는지 여부 쿼리 생성
    boolean existsByPhone(String phone);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    // JPA는 findByXXX을 제공
    User findByPhone(String phone);

    // 해당 JpaRepository는 User 테이블을 관리하는 Repository 이고, Long은 User 테이블의 p.k이다.
    User findByUserId(long userId);

    User findByEmail(String email);

    // phone으로 유저 정보를 가져올 때 권한 정보도 같이 가져옴
    @EntityGraph(attributePaths = "authorities") // Lazy 조회가 아닌 Eager 조회로 authorities 정보를 같이 가져옴
    Optional<User> findOneWithAuthoritiesByPhone(String phone);
}

