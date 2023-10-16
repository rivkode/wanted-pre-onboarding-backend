package com.example.wantedpreonboardingbackend.post.dto.request;

import com.example.wantedpreonboardingbackend.company.Company;
import com.example.wantedpreonboardingbackend.post.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class CreatePostRequest {
    /**
     * 사용자로부터 입력받는 정보
     * 생성시 모든 정보가 필요하므로 NotNull을 통해 null 값을 허용하지 않음
     * 이를 valid를 통해 유효성 검사 진행
     */
    @NotNull
    private String content;
    @NotNull
    private Position position;
    @NotNull
    private int reward;
    @NotNull
    private Skills skills;
    @NotNull
    private Country country;
    @NotNull
    private Region region;

    @NotNull
    private Long companyId;

    public Post toEntity(Company company) {
        return Post.builder()
                .company(company)
                .content(content)
                .position(position)
                .reward(reward)
                .skills(skills)
                .country(country)
                .region(region)
                .build();
    }

}
