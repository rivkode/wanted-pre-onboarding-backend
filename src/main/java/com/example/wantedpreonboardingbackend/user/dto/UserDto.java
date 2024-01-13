package com.example.wantedpreonboardingbackend.user.dto;

import com.example.wantedpreonboardingbackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserDto {
    private Long userId;
    private String username;
    private String skills;
    private String position;

    public static UserDto from(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .skills(user.getSkills())
                .position(user.getPosition())
                .build();
    }

}
