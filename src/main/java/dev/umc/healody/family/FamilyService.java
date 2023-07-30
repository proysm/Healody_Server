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
    public FamilyDTO create(FamilyDTO familyDTO){
        Optional<User> optionalUser = userRepository.findById(familyDTO.getUserId());
        Optional<Home> optionalHome = homeRepository.findById(familyDTO.getHomeId());
        User user = null;
        Home home = null;

        if(optionalUser.isPresent()) user = optionalUser.get();
        if(optionalUser.isPresent()) home = optionalHome.get();
        if(user == null || home == null) return null;

        Family family = familyDTO.toEntity(user, home);
        Family save = familyRepository.save(family);

        return FamilyDTO.builder()
                .userId(save.getUser().getUserId())
                .homeId(save.getHome().getHomeId())
                .build();
    }

    @Transactional(readOnly = true)
    public List<FamilyDTO> findFamily(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = null;

        if(optionalUser.isPresent()) user = optionalUser.get();
        if(user == null) return null;

        List<Family> list = familyRepository.findById(userId);
        return list.stream()
                .map(family -> FamilyDTO.builder()
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
