package dev.umc.healody.home.service;
import dev.umc.healody.home.domain.Home;
import dev.umc.healody.home.dto.HomeDto;
import dev.umc.healody.home.repository.HomeRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public HomeDto createHome(HomeDto homeDto) {
        Home home = homeDto.toEntity();
        Home save = homeRepository.save(home);
        return HomeDto.builder()
                .homeId(save.getHomeId())
                .name(save.getName())
                .build();
    }
    public HomeDto getHomeInfo(Long HomeId){
            Optional<Home> home = homeRepository.findHomeByHomeId(HomeId);
            if(home.isPresent()) {
                return HomeDto.builder()
                        .homeId(home.get().getHomeId())
                        .name(home.get().getName())
                        .build();
            }
            return null;
    }
    @Transactional
    public HomeDto updateHome(Long home_id, HomeDto homeDto) {
            Optional<Home> home = homeRepository.findHomeByHomeId(home_id);
            if(home.isPresent()){
                home.get().setName(homeDto.getName());
                homeRepository.save(home.get());
                HomeDto updatedHome = new HomeDto( home_id, homeDto.getName());
                return updatedHome;
            }
            return null;
    }
    @Transactional
    public void deleteHome(Long home_id) {
        try {
            Optional<Home> home = homeRepository.findHomeByHomeId(home_id);
            home.ifPresent(value -> homeRepository.delete(value));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
