package dev.umc.healody.family;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class MemoryFamilyRepository implements FamilyRepository{

    private static Map<Long, Family> store = new HashMap<>();
    private static Long incrementId = 1L;

    @Override
    public Family save(Family family) {
        family.setId(incrementId);
        store.put(incrementId++, family);
        return family;
    }

    @Override
    public boolean remove(Long user_id, Long home_id) {

        boolean isFind = false;

        //이렇게 처리하는거 매우 안 좋은 방법이지만
        //요건 테스트하기 위해서 작성하는 것이니..
        ArrayList<Family> families = new ArrayList<>(store.values());
        for (int i = 0; i < families.size(); i++){
            if(families.get(i).getUser_id() == user_id &&
                    families.get(i).getHome_id() == home_id){
                isFind = true;
                store.remove(Long.valueOf(i+1));
                break;
            }
        }

        return isFind;
    }

    @Override
    public List<Family> findById(Long user_id) {
        ArrayList<Family> families = new ArrayList<>(store.values());
        ArrayList<Family> result = new ArrayList<Family>();
        for (int i = 0; i < families.size(); i++){
            if(families.get(i).getUser_id() == user_id){
                result.add(families.get(i));
            }
        }

        return result;
    }

    @Override
    public int getFamilyNumber(Long family_id) {
        ArrayList<Family> families = new ArrayList<>(store.values());
        int count = 0;
        for (int i = 0; i < families.size(); i++){
            if(families.get(i).getHome_id() == family_id){
                count++;
            }
        }
        return count;
    }

    @Override
    public List<Family> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsByFamily(Long user_id, Long home_id) {
        return false;
    }

}
