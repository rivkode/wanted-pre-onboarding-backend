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

    @Transactional
    public int createPost(Company company, CreatePostRequest request) {
        int CREATE_POST = 0;

        try {
            postRepository.save(request.toEntity(company));
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

    public int updatePost(Long postId, UpdatePostRequest request) {
        int UPDATE_POST = 0;

        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            try {
                postOptional.get().setContent(request.getContent());
                postOptional.get().setPosition(request.getPosition());
                postOptional.get().setReward(request.getReward());
                postOptional.get().setSkills(request.getSkills());
                postOptional.get().setCountry(request.getCountry());
                postOptional.get().setRegion(request.getRegion());
                log.info("수정된 content : {}", postOptional.get().getContent());
                log.info("수정된 skills : {}", postOptional.get().getSkills());
                postRepository.save(postOptional.get());
            } catch (Exception e) {
                // 추후 예외 처리
            }
        } else {
            throw new CustomException(ErrorCode.INFO_NOT_EXIST);
        }
        return UPDATE_POST;
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
        Specification<Post> spec = search(search);
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
