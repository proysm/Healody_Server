package dev.umc.healody.home.repository;
import dev.umc.healody.home.domain.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



public interface HomeRepository extends JpaRepository<Home, Long>{ //접근할 테이블 클래스, PK의 자료형

    public Optional<Home> findHomeByHomeId(Long id);
}
