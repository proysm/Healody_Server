package dev.umc.healody.family.careuser;

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

    @Transactional
    public CareUserDTO create(CareUserDTO careUserDTO){
        CareUser careUser = careUserDTO.toEntity();
        CareUser save = careUserRepository.save(careUser);

        return CareUserDTO.builder()
                .home_id(save.getHome_id())
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
                .home_id(careUser.getHome_id())
                .nickname(careUser.getNickname())
                .image(careUser.getImage())
                .build());
    }

    @Transactional(readOnly = true)
    public List<CareUserDTO> findCareUsers(Long home_id){
        List<CareUser> careUeres = careUserRepository.findByHomeId(home_id);
        return careUeres.stream()
                .map(careUser -> CareUserDTO.builder()
                        .home_id(careUser.getHome_id())
                        .nickname(careUser.getNickname())
                        .image(careUser.getNickname())
                        .build()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CareUserDTO> findAll(){
        List<CareUser> careUsers = careUserRepository.findAll();

        return careUsers.stream()
                .map(careUser -> CareUserDTO.builder()
                        .home_id(careUser.getHome_id())
                        .nickname(careUser.getNickname())
                        .image(careUser.getNickname())
                        .build()).collect(Collectors.toList());
    }


    @Transactional
    public CareUserDTO update(Long id, CareUserDTO careUserDTO){
        CareUser careUser = careUserRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("CareUser not found"));

        careUserRepository.update(id, careUserDTO.toEntity());
        return  careUserDTO;
    }

    @Transactional
    public void delete(Long id){
        careUserRepository.remove(id);
    }

    @Transactional
    public boolean checkDuplicate(CareUserDTO careUserDTO){
        return careUserRepository.existsCareUser(careUserDTO.toEntity());
    }

    @Transactional
    public Long getCareUserNumber(Long home_id){
        return careUserRepository.getCareUserNumber(home_id);
    }

    //중복 검사
//    private void vaildateDuplcate(CareUserDTO careUser) {
//        careUserRepository.findById(careUser.getId()).ifPresent(i -> {
//            throw new IllegalStateException("이미 존재하는 돌봄입니다.");
//        });
//    }

}
