package com.example.wantedpreonboardingbackend.post.application;

import com.example.wantedpreonboardingbackend.company.Company;
import com.example.wantedpreonboardingbackend.exception.CustomException;
import com.example.wantedpreonboardingbackend.exception.ErrorCode;
import com.example.wantedpreonboardingbackend.post.dao.PostRepository;
import com.example.wantedpreonboardingbackend.post.domain.Post;
import com.example.wantedpreonboardingbackend.post.dto.request.CreatePostRequest;
import com.example.wantedpreonboardingbackend.post.dto.request.UpdatePostRequest;
import com.example.wantedpreonboardingbackend.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public int createPost(Company company, CreatePostRequest request) {
        int CREATE_POST = 0;

        try {
            log.info("service request skills {}", request.getSkills());
            postRepository.save(request.toEntity(company));
            log.info("save complete");
        } catch (Exception e) {
            // 추후 예외 처리
        }
        return CREATE_POST;
    }

    @Transactional
    public int deletePost(Long postId) {
        int DELETE_POST = 0;
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            try {
                postRepository.delete(postOptional.get());
            } catch (Exception e) {
                // 추후 예외 처리
            }
        } else {
            throw new CustomException(ErrorCode.INFO_NOT_EXIST);
        }
        return DELETE_POST;
    }

    public int updatePost(UpdatePostRequest request) {
        int UPDATE_POST = 0;

        Optional<Post> postOptional = postRepository.findById(request.getPostId());
        if (postOptional.isPresent()) {
            setPost(request, postOptional.get());
        } else {
            throw new CustomException(ErrorCode.INFO_NOT_EXIST);
        }
        return UPDATE_POST;
    }

    public void setPost(UpdatePostRequest request, Post post) {
        try {
            post.setContent(request.getContent());
            post.setPosition(request.getPosition());
            post.setReward(request.getReward());
            post.setSkills(request.getSkills());
            post.setCountry(request.getCountry());
            post.setRegion(request.getRegion());
            log.info("수정된 content : {}", post.getContent());
            log.info("수정된 skills : {}", post.getSkills());
            postRepository.save(post);
        } catch (Exception e) {
            // 추후 예외 처리
        }
    }

    public PostResponse getPostDetail(Long postId, Long companyId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        List<Long> postIds = postRepository.findPostIdsByCompanyId(companyId);


        if (postOptional.isPresent()) {
            PostResponse postResponse = PostResponse.detailFrom(postOptional.get(), postIds);
            return postResponse;
        } else {
            throw new CustomException(ErrorCode.DATA_NOT_EXIT);
        }

    }

    public List<PostResponse> getAllPosts() {
        List<PostResponse> postResponseList = new ArrayList<>();
        List<Post> posts = postRepository.findAll();

        for (Post p : posts) {
            postResponseList.add(PostResponse.from(p));
        }

        return postResponseList;
    }

    public List<PostResponse> searchAllPosts(String search) {
        List<PostResponse> postResponseList = new ArrayList<>();
        Specification<Post> spec = this.search(search);
        List<Post> posts = postRepository.findAll(spec);

        for (Post p : posts) {
            postResponseList.add(PostResponse.from(p));
        }
        return postResponseList;
    }

    public Specification<Post> search(String keyword) {
        return new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> post, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); // 중복제거
                Join<Post, Company> company = post.join("company", JoinType.LEFT);
                try {
                    int keywordInt = Integer.parseInt(keyword);
                    return cb.or(
                            cb.like(company.get("name"), "%" + keyword + "%"),
                            cb.like(post.get("position"), "%" + keyword + "%"),
                            cb.like(post.get("reward"), "%" + keywordInt + "%"),
                            cb.like(post.get("skills"), "%" + keyword + "%"),
                            cb.like(post.get("country"), "%" + keyword + "%"),
                            cb.like(post.get("region"), "%" + keyword + "%")
                    );
                } catch (Exception e) {
                    return cb.or(
                            cb.like(company.get("name"), "%" + keyword + "%"),
                            cb.like(post.get("position"), "%" + keyword + "%"),
                            cb.like(post.get("skills"), "%" + keyword + "%"),
                            cb.like(post.get("country"), "%" + keyword + "%"),
                            cb.like(post.get("region"), "%" + keyword + "%")
                    );
                }

            }
        };
    }
}
