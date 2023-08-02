package dev.umc.healody.family;

import dev.umc.healody.home.domain.Home;
import dev.umc.healody.home.repository.HomeRepository;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;

    @Transactional
    public Long create(FamilyRequestDTO requestDTO){
        Optional<User> optionalUser = userRepository.findById(requestDTO.getUserId());
        Optional<Home> optionalHome = homeRepository.findById(requestDTO.getHomeId());
        User user = null;
        Home home = null;

        if(optionalUser.isPresent()) user = optionalUser.get();
        if(optionalUser.isPresent()) home = optionalHome.get();
        if(user == null || home == null) return null;

        Family family = requestDTO.toEntity(user, home);
        Family save = familyRepository.save(family);

        return save.getId();
    }

    @Transactional(readOnly = true)
    public List<FamilyResponseDTO> searchFamily(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = null;

        if(optionalUser.isPresent()) user = optionalUser.get();
        if(user == null) return null;

        List<Family> list = familyRepository.findByUserId(userId);
        return list.stream()
                .map(family -> FamilyResponseDTO.builder()
                        .userId(family.getUser().getUserId())
                        .homeId(family.getHome().getHomeId())
                        .build()).collect(Collectors.toList());
    }

    @Transactional
    public boolean delete(Long userId, Long homeId){
        return familyRepository.remove(userId, homeId);
    }

    @Transactional(readOnly = true)
    public int hasFamilyNumber(Long homeId){
        return familyRepository.getFamilyNumber(homeId);
    }

    @Transactional(readOnly = true)
    public boolean checkFamilyDuplicate(Long userId, Long homeId){
        return  familyRepository.existsByFamily(userId, homeId);
    }

    @Transactional(readOnly = true)
    public boolean checkFamilyOver(Long userId){
        return familyRepository.getFamilyNumber(userId) >= 3;
    }

    @Transactional(readOnly = true)
    public List<Long> searchUserId(Long homeId){
        List<Family> list = familyRepository.findByHomeId(homeId);
        List<Long> result = new ArrayList<Long>();

        for (int i = 0; i < list.size(); i++) {
            result.add(list.get(i).getUser().getUserId());
        }

        return result;
    }
}
