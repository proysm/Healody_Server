package dev.umc.healody.home.service;
import dev.umc.healody.home.domain.Home;
import dev.umc.healody.home.dto.HomeDto;
import dev.umc.healody.home.repository.HomeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
@Service
@NoArgsConstructor
public class HomeService {
    private HomeRepository homeRepository;
    @Autowired
    public HomeService(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }
    @Transactional
    public HomeDto createHome(HomeDto homeDto, Long userId) {
        Home home = homeDto.toEntity();
        home.setAdmin(userId);
        Home save = homeRepository.save(home);
        return HomeDto.builder()
                .homeId(save.getHomeId())
                .name(save.getName())
                .admin(userId)
                .build();
    }
    public HomeDto getHomeInfo(Long HomeId){
            Optional<Home> home = homeRepository.findHomeByHomeId(HomeId);
            if(home.isPresent()) {
                return HomeDto.builder()
                        .homeId(home.get().getHomeId())
                        .name(home.get().getName())
                        .admin(home.get().getAdmin())
                        .build();
            }
            return null;
    }
    @Transactional
    public HomeDto updateHome(Long home_id, HomeDto homeDto, Long currentUserId) {
            Optional<Home> home = homeRepository.findHomeByHomeId(home_id);
            if(home.isPresent()){
                if (!home.get().getAdmin().equals(currentUserId)) {
                    throw new AccessDeniedException("권한이 없습니다.");
                }
                home.get().setName(homeDto.getName());
                homeRepository.save(home.get());
                return HomeDto.builder()
                        .homeId(home_id)
                        .name(homeDto.getName())
                        .build();
            }
            return null;
    }
    @Transactional
    public void deleteHome(Long home_id, Long currentUserId) {
        Optional<Home> home = homeRepository.findHomeByHomeId(home_id);
        if (home.isPresent()) {
            // 현재 사용자가 집의 관리자인지 확인
            if (!home.get().getAdmin().equals(currentUserId)) {
                throw new AccessDeniedException("권한이 없습니다.");
            }

            homeRepository.delete(home.get());
        }
    }
}
