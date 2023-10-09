package com.example.wantedpreonboardingbackend.post.domain;

import com.example.wantedpreonboardingbackend.company.Company;
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

    public void setContent(String content) {
        this.content = content;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRegion(String region) {
        this.region = region;
    }

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
    }
}
