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
    public boolean remove(Long user_id, Long home_id) {
        List<Family> families = em.createQuery("select f from Family f where f.user_id = :user_id and f.home_id = :home_id", Family.class)
                .setParameter("user_id", user_id)
                .setParameter("home_id", home_id)
                .getResultList();

        if(families.isEmpty()) return false;

        families.forEach(em::remove);
        return true;
    }

    @Override
    public List<Family> findById(Long user_id) {
        return em.createQuery("select f from Family f where f.user_id = :user_id", Family.class)
                .setParameter("user_id", user_id)
                .getResultList();
    }

    @Override
    public int getFamilyNumber(Long user_id) {
        Long count = em.createQuery("select count(f) from Family f where f.user_id = :user_id", Long.class)
                .setParameter("user_id", user_id)
                .getSingleResult();
        return count.intValue();
    }

    @Override
    public List<Family> findAll() {
        return em.createQuery("select f from Family f", Family.class)
                .getResultList();
    }

    @Override
    public boolean existsByFamily(Long user_id, Long home_id) {
        Long count = em.createQuery("select count(f) from Family f where f.user_id = :user_id and f.home_id = :home_id", Long.class)
                .setParameter("user_id", user_id)
                .setParameter("home_id", home_id)
                .getSingleResult();
        return count > 0;
    }

}