package dev.umc.healody.user.kakao.repository;

import dev.umc.healody.user.kakao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;


public interface UserRepository extends JpaRepository<User, Long> {
// DAO와 같은 것! 자동으로 bean으로 등록이 된다. -> @Repository 생략 가능해짐.
// 해당 JpaRepository는 User 테이블을 관리하는 Repository 이고, Long은 User 테이블의 p.k이다.

    @Query(value = "select * from User where user_id = ?", nativeQuery = true)
    User findByUserId(long userId);
}
