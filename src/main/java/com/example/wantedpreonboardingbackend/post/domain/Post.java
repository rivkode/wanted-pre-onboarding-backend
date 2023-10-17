package com.example.wantedpreonboardingbackend.post.domain;

import com.example.wantedpreonboardingbackend.company.Company;
import com.example.wantedpreonboardingbackend.post.dto.request.UpdatePostRequest;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE post SET is_deleted = true WHERE post_id = ?")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String content;

    @Enumerated(EnumType.STRING)
    private Position position;

    private int reward;

    @Enumerated(EnumType.STRING)
    private Skills skills;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Builder
    private Post(Company company, String content, Position position, int reward, Skills skills, Country country,
                 Region region) {
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
