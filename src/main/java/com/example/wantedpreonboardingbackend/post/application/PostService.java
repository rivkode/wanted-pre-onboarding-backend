package com.example.wantedpreonboardingbackend.post.application;

import com.example.wantedpreonboardingbackend.company.Company;
import com.example.wantedpreonboardingbackend.exception.CustomException;
import com.example.wantedpreonboardingbackend.exception.ErrorCode;
import com.example.wantedpreonboardingbackend.post.dao.PostRepository;
import com.example.wantedpreonboardingbackend.post.domain.Post;
import com.example.wantedpreonboardingbackend.post.dto.request.CreatePostRequest;
import com.example.wantedpreonboardingbackend.post.dto.request.UpdatePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public int createPost(Company company, CreatePostRequest request) {
        int POST_CREATE = 0;

        try {
            postRepository.save(request.toEntity(company));
        } catch (Exception e) {
            // 추후 예외 처리
        }
        return POST_CREATE;
    }

    @Transactional
    public int deletePost(Long postId) {
        int POST_DELETE = 0;
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            try {
                postRepository.delete(postOptional.get());
            } catch (Exception e) {
                // 추후 예외 처리
            }
        } else {

        }
        return POST_DELETE;
    }

//    public int updatePost(Long postId, UpdatePostRequest request) {
//        Optional<Post> postOptional = postRepository.findById(postId);
//        if (postOptional.isPresent()) {
//            try {
//                postOptional.
//            }
//        }
//    }
}
