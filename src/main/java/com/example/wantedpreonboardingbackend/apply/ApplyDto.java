package com.example.wantedpreonboardingbackend.apply;

import com.example.wantedpreonboardingbackend.post.domain.Post;
import com.example.wantedpreonboardingbackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApplyDto {
    private Long userId;
    private Long postId;

    public Apply toEntity(User user, Post post) {
        return Apply.builder()
                .user(user)
                .post(post)
                .build();
    }
}
