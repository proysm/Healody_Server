package dev.umc.healody.careuser;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MemoryCareUserRepository implements CareUserRepository {

    private static Map<Long, CareUser> store = new HashMap<>();
    private static Long incrementId = 1L;

    @Override
    public CareUser save(CareUser careUser) {
        if(!findById(careUser.getId()).equals(Optional.empty())) return null;

        careUser.setId(incrementId);
        store.put(incrementId++, careUser);
        return careUser;
    }

    @Override
    public boolean update(Long id, CareUser careUser) {
        if(!findById(careUser.getId()).equals(Optional.empty())) return false;
        CareUser find = findById(id).get();
        find.update(careUser.getNickname(), careUser.getFilename());
        return true;
    }

    @Override
    public boolean remove(Long id) {
        if(findById(id).equals(Optional.empty())) return false;

        store.remove(id);
        return true;
    }

    @Override
    public Optional<CareUser> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<CareUser> findAll() {
        return new ArrayList<>(store.values());
    }
}
