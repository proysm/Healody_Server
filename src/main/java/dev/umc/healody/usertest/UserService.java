package dev.umc.healody.usertest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        return userRepository.save(user).getId();
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }
}
