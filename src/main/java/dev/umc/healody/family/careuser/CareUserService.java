package dev.umc.healody.family.careuser;

import dev.umc.healody.home.domain.Home;
import dev.umc.healody.home.repository.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareUserService {
    private final CareUserRepository careUserRepository;
    private final HomeRepository homeRepository;

    @Transactional
    public CareUserDTO create(CareUserDTO careUserDTO){

        Optional<Home> optionalHome = homeRepository.findById(careUserDTO.getHomeId());
        Home home = null;

        if(optionalHome.isPresent()) home = optionalHome.get();
        else return null;

        CareUser careUser = careUserDTO.toEntity(home);
        CareUser save = careUserRepository.save(careUser);

        return CareUserDTO.builder()
                .id(save.getId())
                .homeId(save.getHome().getHomeId())
                .nickname(save.getNickname())
                .image(save.getImage())
                .build();
    }

    @Transactional(readOnly = true)
    public Optional<CareUserDTO> findOne(Long id){
        Optional<CareUser> optionalCareUser = careUserRepository.findById(id);

        if(optionalCareUser.isEmpty()) return Optional.empty();

        CareUser careUser = optionalCareUser.get();
        return Optional.of(CareUserDTO.builder()
                .id(careUser.getId())
                .homeId(careUser.getHome().getHomeId())
                .nickname(careUser.getNickname())
                .image(careUser.getImage())
                .build());
    }

    @Transactional(readOnly = true)
    public List<CareUserDTO> findCareUsers(Long home_id){
        List<CareUser> careUeres = careUserRepository.findByHomeId(home_id);
        return careUeres.stream()
                .map(careUser -> CareUserDTO.builder()
                        .id(careUser.getId())
                        .homeId(careUser.getHome().getHomeId())
                        .nickname(careUser.getNickname())
                        .image(careUser.getNickname())
                        .build()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CareUserDTO> findAll(){
        List<CareUser> careUsers = careUserRepository.findAll();

        return careUsers.stream()
                .map(careUser -> CareUserDTO.builder()
                        .id(careUser.getId())
                        .homeId(careUser.getHome().getHomeId())
                        .nickname(careUser.getNickname())
                        .image(careUser.getNickname())
                        .build()).collect(Collectors.toList());
    }


    @Transactional
    public CareUserDTO update(Long id, CareUserDTO careUserDTO){
        CareUser build = CareUser.builder()
                .nickname(careUserDTO.getNickname())
                .image(careUserDTO.getImage())
                .build();

        boolean result = careUserRepository.update(id, build);

        if(result == false) return null;

        return  CareUserDTO.builder()
                .id(id)
                .nickname(careUserDTO.getNickname())
                .homeId(careUserDTO.getHomeId())
                .image(careUserDTO.getImage())
                .build();
    }

    @Transactional
    public void delete(Long id){
        careUserRepository.remove(id);
    }

    @Transactional
    public boolean checkDuplicate(Long home_id, String nickname){
        return careUserRepository.existsCareUser(home_id, nickname);
    }

    @Transactional
    public boolean checkCareUserOver(Long userId){
        return careUserRepository.getCareUserNumber(userId) >= 4;
    }

    //중복 검사
//    private void vaildateDuplcate(CareUserDTO careUser) {
//        careUserRepository.findById(careUser.getId()).ifPresent(i -> {
//            throw new IllegalStateException("이미 존재하는 돌봄입니다.");
//        });
//    }

}
