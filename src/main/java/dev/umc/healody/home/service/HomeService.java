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
                .admin(save.getAdmin())
                .info(save.getInfo())
                .build();
    }
    public HomeDto getHomeInfo(Long HomeId){
            Optional<Home> home = homeRepository.findHomeByHomeId(HomeId);
            if(home.isPresent()) {
                return HomeDto.builder()
                        .homeId(home.get().getHomeId())
                        .name(home.get().getName())
                        .admin(home.get().getAdmin())
                        .info(home.get().getInfo())
                        .build();
            }
            return null;
    }
    @Transactional
    public HomeDto updateHome(Long home_id, HomeDto homeDto) {
            Optional<Home> home = homeRepository.findHomeByHomeId(home_id);
            home.get().setName(homeDto.getName());
            home.get().setInfo(homeDto.getInfo());
            Home updatedHome = homeRepository.save(home.get());
            return HomeDto.builder()
                    .homeId(updatedHome.getHomeId())
                    .name(updatedHome.getName())
                    .admin(updatedHome.getAdmin())
                    .info(updatedHome.getInfo())
                    .build();
    }
    @Transactional
    public void deleteHome(Long home_id) {
        Optional<Home> home = homeRepository.findHomeByHomeId(home_id);
        if (home.isPresent()) {
            homeRepository.delete(home.get());
        }
    }
}
