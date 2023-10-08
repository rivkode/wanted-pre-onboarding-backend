package com.example.wantedpreonboardingbackend.post.dto.request;

import com.example.wantedpreonboardingbackend.company.Company;
import com.example.wantedpreonboardingbackend.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePostRequest {
    /**
     * 사용자로부터 입력받는 정보
     */
    private String content;
    private String position;
    private int reward;
    private String skills;
    private String country;
    private String region;

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
