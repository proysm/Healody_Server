package dev.umc.healody.family;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoryFamilyRepositoryTest {

    private FamilyRepository repository = new MemoryFamilyRepository();

    @Test
    void create(){
        Family family = Family.builder()
                .family_id(1L)
                .user_id(1L)
                .build();
        family.setId(1L);
        repository.save(family);

        Family family1 = Family.builder()
                .family_id(1L)
                .user_id(1L)
                .build();
        family1.setId(2L);
        repository.save(family1);

        List<Family> getList = repository.findById(1L);

        Assertions.assertThat(getList.size()).isEqualTo(2);
    }

    @Test
    void delete(){
        Family family = Family.builder()
                .family_id(1L)
                .user_id(1L)
                .build();
        family.setId(1L);
        repository.save(family);

        boolean result = repository.remove(1L, 1L);
        Assertions.assertThat(result).isEqualTo(true);

        List<Family> getList = repository.findById(1L);
        Assertions.assertThat(getList.size()).isEqualTo(0);
    }

}