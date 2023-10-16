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
    public PostResponse createPost(Company company, CreatePostRequest request) {

        try {
            log.info("service request skills {}", request.getSkills());
            Post savedPost = postRepository.save(request.toEntity(company));
            log.info("save complete");
            return PostResponse.from(savedPost);
        } catch (Exception e) {
            log.error("create Post Exception {}", e);
            throw new CustomException(ErrorCode.INVALID_POST);
        }
    }

    @Transactional
    public PostResponse deletePost(Long postId) {
        Post post = findPostById(postId);
        try {
            postRepository.delete(post); // soft delete로 변경예정
            return PostResponse.from(savedPost);
        } catch (Exception e) {
            log.error("delete Post Exception {}", e);
            throw new CustomException(ErrorCode.INVALID_POST);
        }
    }

    @Transactional
    public PostResponse updatePost(UpdatePostRequest request) {
        try {
            Post post = findPostById(request.getPostId());
            log.info("before update post position {}", post.getPosition());
            Post updatedPost = post.updatePost(request);
            postRepository.save(updatedPost);
            log.info("after update post position {}", updatedPost.getPosition());
            return PostResponse.from(updatedPost);
        } catch (Exception e) {
            log.error("update Post Exception {}", e);
            throw new CustomException(ErrorCode.INVALID_POST);
        }
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIT));
    }

    public PostResponse getPostDetail(Long postId, Long companyId) {
        Post post = findPostById(postId);
        List<Long> postIds = postRepository.findPostIdsByCompanyId(companyId);
        PostResponse postResponse = PostResponse.detailFrom(post, postIds);
        return postResponse;
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

        // 동적으로 query 생성
        Specification<Post> spec = this.search(search);

        List<Post> posts = postRepository.findAll(spec);

        // Post 객체를 PostResponse로 변환
        for (Post p : posts) {
            postResponseList.add(PostResponse.from(p));
        }
        return postResponseList;
    }

    public Specification<Post> search(String keyword) {
        return new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> post, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // 중복제거
                query.distinct(true);

                // Post table과 Company table을 Outer Join 수행
                Join<Post, Company> company = post.join("company", JoinType.LEFT);
                try {
                    int keywordInt = Integer.parseInt(keyword);
                    return cb.or(
                            // join한 table을 통해 name column 검색
                            cb.like(company.get("name"), "%" + keyword + "%"),
                            cb.like(post.get("position"), "%" + keyword + "%"),
                            cb.like(post.get("reward"), "%" + keywordInt + "%"),
                            cb.like(post.get("skills"), "%" + keyword + "%"),
                            cb.like(post.get("country"), "%" + keyword + "%"),
                            cb.like(post.get("region"), "%" + keyword + "%")
                    );
                } catch (Exception e) {
                    return cb.or(
                            // join한 table을 통해 name column 검색
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
