package dev.umc.healody.family;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;

    @Transactional
    public FamilyDTO create(FamilyDTO familyDTO){
        Family family = familyDTO.toEntity();
        Family save = familyRepository.save(family);

        return FamilyDTO.builder()
                .user_id(save.getUser_id())
                .family_id(save.getFamily_id())
                .build();
    }

    @Transactional(readOnly = true)
    public List<FamilyDTO> findFamily(Long userId){
        List<Family> list = familyRepository.findById(userId);
        return list.stream()
                .map(family -> FamilyDTO.builder()
                        .user_id(family.getUser_id())
                        .family_id(family.getFamily_id())
                        .build()).collect(Collectors.toList());
    }

    @Transactional
    public boolean delete(Long userId, Long familyId){
        return familyRepository.remove(userId, familyId);
    }

    @Transactional(readOnly = true)
    public int getFamilyNumber(Long familyId){
        return familyRepository.getFamilyNumber(familyId);
    }

    @Transactional
    public boolean checkFamilyDuplicate(Long userId, Long familyId){
        return  familyRepository.existsByFamily(userId, familyId);
    }

    @Transactional
    public boolean checkFamilyOver(Long userId){
        return familyRepository.getFamilyNumber(userId) >= 3;
    }
}
