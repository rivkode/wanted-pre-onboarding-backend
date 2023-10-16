package com.example.wantedpreonboardingbackend.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class UpdatePostRequest {
    /**
     * Post 수정이며 모든 값에 대해 요청하며 전달받은 값으로 모든 값 업데이트
     * Valid를 통해 유효성 검증 진행
     */
    @NotNull
    private String content;

    @NotNull
    private String position;

    @NotNull
    private int reward;

    @NotNull
    private String skills;

    @NotNull
    private String country;

    @NotNull
    private String region;

    @NotNull
    private Long companyId;

    @NotNull
    private Long postId;
}
