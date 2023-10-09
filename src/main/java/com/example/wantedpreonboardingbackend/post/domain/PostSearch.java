package com.example.wantedpreonboardingbackend.post.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearch {
    private String companyName;
    private String country;
    private String region;
    private String position;
}
