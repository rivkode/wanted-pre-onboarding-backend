package com.example.wantedpreonboardingbackend.post.domain;

import com.example.wantedpreonboardingbackend.company.Company;
import com.example.wantedpreonboardingbackend.post.dto.request.UpdatePostRequest;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String content;

    private String position;

    private int reward;

    private String skills;

    private String country;

    private String region;

    private boolean isDeleted;

    @Builder
    private Post(Company company, String content, String position, int reward, String skills, String country,
                 String region) {
        this.company = company;
        this.content = content;
        this.position = position;
        this.reward = reward;
        this.skills = skills;
        this.country = country;
        this.region = region;
        this.isDeleted = false;
    }

    public Post updatePost(UpdatePostRequest request) {
        this.content = request.getContent();
        this.position = request.getPosition();
        this.reward = request.getReward();
        this.skills = request.getSkills();
        this.country = request.getCountry();
        this.region = request.getRegion();

        return this;
    }

    public void deletePost() {
        this.isDeleted = true;
    }

}
