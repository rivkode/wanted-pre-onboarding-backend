package com.example.wantedpreonboardingbackend.apply;

import com.example.wantedpreonboardingbackend.post.domain.Post;
import com.example.wantedpreonboardingbackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ApplyDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long postId;

    public Apply toEntity(User user, Post post) {
        return Apply.builder()
                .user(user)
                .post(post)
                .build();
    }

    public static ApplyDto from(Apply apply) {
        return new ApplyDto(apply.getUser().getId(), apply.getPost().getId());
    }
}
