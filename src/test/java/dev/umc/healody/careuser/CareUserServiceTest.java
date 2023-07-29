package dev.umc.healody.careuser;

import dev.umc.healody.family.careuser.CareUserDTO;
import dev.umc.healody.family.careuser.CareUserRepository;
import dev.umc.healody.family.careuser.CareUserService;
import dev.umc.healody.family.careuser.MemoryCareUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
/*
@SpringBootTest
class CareUserServiceTest {
    @MockBean
    private CareUserRepository careUserRepository;
    @MockBean
    private CareUserService careUserService;
* */
class CareUserServiceTest {

    private CareUserService careUserService;
    private CareUserRepository careUserRepository;

    @BeforeEach
    public void BeforeEach(){
        careUserRepository = new MemoryCareUserRepository();
        careUserService = new CareUserService(careUserRepository);
    }

    @Test
    void Add(){
        CareUserDTO careUserDTO = CareUserDTO.builder()
                .home_id(1L)
                .nickname("test-nickname")
                .image("test-filename")
                .build();
        CareUserDTO careUserDTO1 = careUserService.create(careUserDTO);
        CareUserDTO careUserDTO2 = careUserService.findOne(1L).get();
        Assertions.assertThat(careUserDTO1.getNickname()).isEqualTo(careUserDTO2.getNickname());
    }

    @Test
    void Remove(){
        CareUserDTO careUserDTO = CareUserDTO.builder()
                .home_id(1L)
                .nickname("test-nickname")
                .image("test-filename")
                .build();
        careUserService.create(careUserDTO);
        careUserService.delete(1L);
        Optional<CareUserDTO> optionalCareUserDTO = careUserService.findOne(1L);
        Assertions.assertThat(optionalCareUserDTO.isEmpty()).isTrue();
    }

    @Test
    void Update(){
        CareUserDTO careUserDTO = CareUserDTO.builder()
                .home_id(1L)
                .nickname("test-nickname")
                .image("test-filename")
                .build();
        careUserService.create(careUserDTO);

        CareUserDTO edit = CareUserDTO.builder()
                .home_id(1L)
                .nickname("test-edit-nickname")
                .image("test-edit-filename")
                .build();
        CareUserDTO update = careUserService.update(1L, edit);
        Assertions.assertThat(update.getImage()).isEqualTo("test-edit-filename");
    }
}