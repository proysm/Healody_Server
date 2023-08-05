package dev.umc.healody.family;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Primary
public class JpaFamilyRepository implements FamilyRepository{

    private final EntityManager em;

    @Override
    public Family save(Family family) {
        em.persist(family);
        return family;
    }

    @Override
    public boolean remove(Long userId, Long homeId) {
        List<Family> families = em.createQuery("select f from Family f where f.user.userId = :user_id and f.home.homeId = :home_id", Family.class)
                .setParameter("user_id", userId)
                .setParameter("home_id", homeId)
                .getResultList();

        if(families.isEmpty()) return false;

        families.forEach(em::remove);
        return true;
    }

    @Override
    public List<Family> findByUserId(Long userId) {
        return em.createQuery("select f from Family f where f.user.userId = :user_id", Family.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Family> findByHomeId(Long homeId) {
        return em.createQuery("select f from Family f where f.home.homeId = :home_id", Family.class)
                .setParameter("home_id", homeId)
                .getResultList();
    }

    @Override
    public int getFamilyNumber(Long userId) {
        Long count = em.createQuery("select count(f) from Family f where f.user.userId = :user_id", Long.class)
                .setParameter("user_id", userId)
                .getSingleResult();
        return count.intValue();
    }

    @Override
    public List<Family> findAll() {
        return em.createQuery("select f from Family f", Family.class)
                .getResultList();
    }

    @Override
    public boolean existsByFamily(Long userId, Long homeId) {
        Long count = em.createQuery("select count(f) from Family f where f.user.userId = :user_id and f.home.homeId = :home_id", Long.class)
                .setParameter("user_id", userId)
                .setParameter("home_id", homeId)
                .getSingleResult();
        return count > 0;
    }

}