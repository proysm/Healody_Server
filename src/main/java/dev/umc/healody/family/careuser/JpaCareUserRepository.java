package dev.umc.healody.family.careuser;

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
    public List<CareUser> findAll() {
        return em.createQuery("select c from CareUser c", CareUser.class).getResultList();
    }
}