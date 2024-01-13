package com.example.wantedpreonboardingbackend.user;

import com.example.wantedpreonboardingbackend.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getUser(Long userId) {
        return UserDto.from(userRepository.findById(userId).get());
    }

    public void createUser() {
        User user = User.builder()
                .username("username")
                .skills("skills")
                .position("position")
                .build();
        userRepository.save(user);
    }
}
