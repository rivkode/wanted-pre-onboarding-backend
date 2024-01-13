package com.example.wantedpreonboardingbackend.user;

import com.example.wantedpreonboardingbackend.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public String createUser() {
        userService.createUser();
        return "success user";
    }

    @GetMapping("/user/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

}
