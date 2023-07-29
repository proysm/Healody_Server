package dev.umc.healody.careuser;

import dev.umc.healody.family.careuser.CareUser;
import dev.umc.healody.family.careuser.CareUserRepository;
import dev.umc.healody.family.careuser.MemoryCareUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class MemoryCareUserRepositoryTest {

    private CareUserRepository careUserRepository = new MemoryCareUserRepository();

    @Test
    void create(){
        CareUser careUser = CareUser.builder()
                            .homeId(1L)
                            .nickname("test-nickname")
                            .filename("test-filename").build();
        careUserRepository.save(careUser);
        CareUser getCareUser = careUserRepository.findById(1L).get();
        Assertions.assertThat(careUser).isEqualTo(getCareUser);
    }


    @Test
    void delete(){
        CareUser careUser = CareUser.builder()
                .homeId(1L)
                .nickname("test-nickname")
                .filename("test-filename").build();
        careUserRepository.save(careUser);
        careUserRepository.remove(careUser.getId());
        Optional<CareUser> getCareUser = careUserRepository.findById(careUser.getId());
        Assertions.assertThat(getCareUser.isEmpty()).isTrue();
    }
}