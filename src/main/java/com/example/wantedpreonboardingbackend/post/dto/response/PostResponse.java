package com.example.wantedpreonboardingbackend.post.dto.response;

import com.example.wantedpreonboardingbackend.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostResponse {
    private Long postId;
    private String companyName;
    private String country;
    private String region;
    private String position;
    private int reward;
    private String content;
    private String skills;

    public static PostResponse of(Long postId ,String companyName, String position,String country, String region,
                                  int reward, String content, String skills) {
        return new PostResponse(postId, companyName, position, country, region, reward, content, skills);
    }

    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getCompany().getName(), post.getCountry(), post.getRegion(), post.getPosition(), post.getReward(),
                post.getContent(), post.getSkills());
    }
}
