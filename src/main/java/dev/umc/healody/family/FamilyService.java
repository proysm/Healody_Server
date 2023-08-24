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

import static dev.umc.healody.common.FindUserInfo.getCurrentUserId;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;

    @Transactional
    public Long create(FamilyRequestDTO requestDTO){
        if(requestDTO.getUserId() == null) return null;

        Optional<User> optionalUser = userRepository.findById(requestDTO.getUserId());
        Optional<Home> optionalHome = homeRepository.findById(requestDTO.getHomeId());
        User user = null;
        Home home = null;

        if(optionalUser.isPresent()) user = optionalUser.get();
        if(optionalHome.isPresent()) home = optionalHome.get();
        if(checkFamilyOver(requestDTO.getUserId()) ||
                checkFamilyMemberOver(requestDTO.getHomeId()) ||
                checkFamilyDuplicate(requestDTO.getUserId(), requestDTO.getHomeId())
                || user == null || home == null){
            return null;
        }

        Family family = requestDTO.toEntity(user, home);
        home.setUser_cnt(home.getUser_cnt() + 1);
        user.setFamilyCnt(user.getFamilyCnt() + 1);
        Family save = familyRepository.save(family);
        return save.getId();
    }

    @Transactional(readOnly = true)
    public List<FamilyResponseDTO> searchFamily(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = null;

        if(optionalUser.isPresent()) user = optionalUser.get();

        List<Family> list = familyRepository.findByUserId(userId);
        return list.stream()
                .map(family -> FamilyResponseDTO.builder()
                        .userId(family.getUser().getUserId())
                        .homeId(family.getHome().getHomeId())
                        .build()).collect(Collectors.toList());
    }

    @Transactional
    public boolean delete(Long userId, Long homeId){
        Optional<Home> optionalHome = homeRepository.findHomeByHomeId(homeId);
        Optional<User> optionalUser = userRepository.findById(userId);
        Home home = null;
        User user = null;

        if(optionalUser.isPresent()) user = optionalUser.get();
        if(optionalHome.isPresent()) home = optionalHome.get();
        user.setFamilyCnt(user.getFamilyCnt() - 1);
        home.setUser_cnt(home.getUser_cnt() - 1);
        if(home == null) return false;

        return familyRepository.remove(userId, homeId);
    }

    @Transactional
    public boolean update(Long userId, Long homeId, Long changeHomeId){
        Long result = create(FamilyRequestDTO.builder().userId(userId).homeId(changeHomeId).build());

        if (result == null) return false;
        else return delete(userId, homeId);
    }


    @Transactional(readOnly = true)
    public boolean checkFamilyDuplicate(Long userId, Long homeId){
        return  familyRepository.existsByFamily(userId, homeId);
    }

    @Transactional(readOnly = true)
    public boolean checkFamilyOver(Long userId){
        return familyRepository.findByUserId(userId).size() >= 3;
    }

    @Transactional(readOnly = true)
    public boolean checkFamilyMemberOver(Long homeId){
        return familyRepository.findByHomeId(homeId).size() >= 6;
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

    public boolean isAdmin(Long userId, Long homeId){
        Optional<Home> optionalHome = homeRepository.findHomeByHomeId(homeId);
        Home home = null;
        if(optionalHome.isPresent()) home = optionalHome.get();
        if (home.getAdmin().equals(userId)) return true;
        return false;
    }
}
