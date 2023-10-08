package com.example.wantedpreonboardingbackend.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePostResponse {
    private Long companyId;
    private String position;
    private int reward;
    private String content;
    private String skills;

    public static CreatePostResponse of(Long companyId, String position, int reward, String content, String skills) {
        return new CreatePostResponse(companyId, position, reward, content, skills);
    }
}
