package com.example.wantedpreonboardingbackend.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class UpdatePostRequest {
    /**
     * Post 부분 수정이므로 companyId와 postId에 대해서만 NotNull 적용
     * Valid를 통해 유효성 검증 진행
     */
    private String content;
    private String position;
    private int reward;
    private String skills;
    private String country;
    private String region;

    @NotNull
    private Long companyId;

    @NotNull
    private Long postId;
}
