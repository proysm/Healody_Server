package dev.umc.healody.family.careuser;

import dev.umc.healody.family.Family;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Primary
public class JpaCareUserRepository implements CareUserRepository{

    private final EntityManager em;

    @Override
    public CareUser save(CareUser careUser) {
        em.persist(careUser);
        return careUser;
    }

    @Override
    public boolean update(Long id, CareUser updatedCareUser) {
        CareUser careUser = em.find(CareUser.class, id);
        if(careUser != null){
            careUser.update(updatedCareUser.getNickname(), updatedCareUser.getImage());
            return true;
        }

        return false;
    }

    @Override
    public boolean remove(Long id) {
        CareUser careUser = em.find(CareUser.class, id);
        if(careUser != null){
            em.remove(careUser);
            return true;
        }

        return false;
    }

    @Override
    public Optional<CareUser> findById(Long id) {
        CareUser careUser = em.find(CareUser.class, id);
        return Optional.ofNullable(careUser);
    }

    @Override
    public List<CareUser> findByHomeId(Long home_id) {
        return em.createQuery("select f from CareUser f where f.home_id = :home_id", CareUser.class)
                .setParameter("home_id", home_id)
                .getResultList();
    }

    @Override
    public List<CareUser> findAll() {
        return em.createQuery("select c from CareUser c", CareUser.class).getResultList();
    }

    @Override
    public boolean existsCareUser(Long home_id, String nickname) {
        Long count = em.createQuery("select count(f) from CareUser f where f.home_id = :home_id and f.nickname = :nickname", Long.class)
                .setParameter("home_id", home_id)
                .setParameter("nickname", nickname)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public Long getCareUserNumber(Long home_id) {
        Long count = em.createQuery("select count(f) from CareUser f where f.home_id = :home_id", Long.class)
                .setParameter("home_id", home_id)
                .getSingleResult();
        return count;
    }
}