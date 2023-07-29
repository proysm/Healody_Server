package dev.umc.healody.user.service;

import dev.umc.healody.user.dto.UserDto;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserDto userDTO) {
        // UserDTO에서 필요한 데이터를 추출하여 User 엔티티로 변환
        User user = new User();
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setBirth(userDTO.getBirth());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setNickname(userDTO.getNickname());
        user.setPassword(userDTO.getPassword());

        userRepository.save(user);
    }

//    public JwtToken login(String phone, String password) {
//        User user = userRepository.findByPhone(loginDto.getPhone());
//        System.out.println(user);
//
//        if(user.orElse(null) == null || !){
//            return false;
//        }
//        if(!findUser.getPassword().equals(user.getPassword())){
//            return false;
//        }
//
//        return true;
//    }

    @Transactional(readOnly = true)
    public boolean checkPhoneDuplication(String phone) {
        boolean usernameDuplicate = userRepository.existsByPhone(phone);
        return usernameDuplicate;
    }

    @Transactional(readOnly = true)
    public boolean checkNicknameDuplication(String nickname) {
        boolean nicknameDuplicate = userRepository.existsByNickname(nickname);
        return nicknameDuplicate;

    }

    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email) {
        boolean emailDuplicate = userRepository.existsByEmail(email);
        return emailDuplicate;
    }

    @Transactional(readOnly = true)
    public Long findUserIdByPhone(String phone) {
        User user = userRepository.findByPhone(phone);
        if (user != null) {
            return user.getId();
        }
        return null; // 해당 전화번호로 유저를 찾지 못한 경우
    }
}
