package com.example.wantedpreonboardingbackend.post.dto.response;

import com.example.wantedpreonboardingbackend.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

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
    private List<Long> companyIds;

    public PostResponse(Long postId, String companyName, String country, String region, String position, int reward, String skills) {
        this.postId = postId;
        this.companyName = companyName;
        this.country = country;
        this.region = region;
        this.position = position;
        this.reward = reward;
        this.skills = skills;
    }



    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getCompany().getName(), post.getCountry(), post.getRegion(), post.getPosition(), post.getReward(), post.getSkills());
    }

    public static PostResponse detailFrom(Post post, List<Long> companyIds) {
        return new PostResponse(post.getId(), post.getCompany().getName(), post.getCountry(), post.getRegion(), post.getPosition(), post.getReward(),
                post.getContent(), post.getSkills(), companyIds);
    }
}
