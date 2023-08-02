package dev.umc.healody.family.careuser;

import dev.umc.healody.home.domain.Home;
import dev.umc.healody.home.repository.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public Long create(CareUserRequestDTO requestDTO){
        Optional<Home> optionalHome = homeRepository.findById(requestDTO.getHomeId());
        Home home = null;

        //중복, 돌볼계정 수 오버, 집 존재 x
        if(checkDuplicate(requestDTO.getHomeId(), requestDTO.getNickname()) ||
                checkCareUserOver(requestDTO.getHomeId()) ||
                optionalHome.isEmpty()){
            return  -1L;
        }

        CareUser careUser = requestDTO.toEntity(optionalHome.get());
        CareUser save = careUserRepository.save(careUser);

        return save.getId();
    }

    @Transactional(readOnly = true)
    public Optional<CareUserResponseDTO> findCareUser(Long id){
        Optional<CareUser> optionalCareUser = careUserRepository.findById(id);
        if(optionalCareUser.isEmpty()) return Optional.empty();

        CareUser careUser = optionalCareUser.get();
        CareUserResponseDTO responseDTO = new CareUserResponseDTO();
        return Optional.of(responseDTO.toDTO(careUser));
    }

    @Transactional(readOnly = true)
    public List<CareUserResponseDTO> findCareUsers(Long home_id){
        List<CareUser> careUeres = careUserRepository.findByHomeId(home_id);
        return careUeres.stream()
                .map(careUser -> CareUserResponseDTO.builder()
                        .id(careUser.getId())
                        .homeId(careUser.getHome().getHomeId())
                        .nickname(careUser.getNickname())
                        .image(careUser.getNickname())
                        .build()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CareUserResponseDTO> findAll(){
        List<CareUser> careUsers = careUserRepository.findAll();

        return careUsers.stream()
                .map(careUser -> CareUserResponseDTO.builder()
                        .id(careUser.getId())
                        .homeId(careUser.getHome().getHomeId())
                        .nickname(careUser.getNickname())
                        .image(careUser.getNickname())
                        .build()).collect(Collectors.toList());
    }


    @Transactional
    public boolean update(Long id, CareUserRequestDTO requestDTO){
        Optional<CareUser>careUser = careUserRepository.findById(id);

        if (careUser.isEmpty()) return false;

        return careUserRepository.update(id,
                CareUser.builder()
                .nickname(requestDTO.getNickname())
                .image(requestDTO.getImage())
                .build());
    }

    @Transactional
    public boolean delete(Long id){
        Optional<CareUser>careUser = careUserRepository.findById(id);
        if (careUser.isEmpty()) return false;

        return careUserRepository.remove(id);
    }

    @Transactional
    public boolean checkDuplicate(Long home_id, String nickname){
        return careUserRepository.existsCareUser(home_id, nickname);
    }

    @Transactional
    public boolean checkCareUserOver(Long userId){
        return careUserRepository.getCareUserNumber(userId) >= 4;
    }
}
