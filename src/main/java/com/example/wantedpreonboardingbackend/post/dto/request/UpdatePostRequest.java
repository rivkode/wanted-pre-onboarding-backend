package com.example.wantedpreonboardingbackend.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdatePostRequest {
    private String content;
    private String position;
    private int reward;
    private String skills;
    private String country;
    private String region;
    private Long companyId;
    private Long postId;
}
