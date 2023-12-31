package com.example.wantedpreonboardingbackend.post.api;

import com.example.wantedpreonboardingbackend.common.dto.ApiResponse;
import com.example.wantedpreonboardingbackend.company.Company;
import com.example.wantedpreonboardingbackend.company.CompanyService;
import com.example.wantedpreonboardingbackend.exception.CustomException;
import com.example.wantedpreonboardingbackend.exception.ErrorCode;
import com.example.wantedpreonboardingbackend.exception.SuccessCode;
import com.example.wantedpreonboardingbackend.post.application.PostService;
import com.example.wantedpreonboardingbackend.post.dto.request.CreatePostRequest;
import com.example.wantedpreonboardingbackend.post.dto.request.UpdatePostRequest;
import com.example.wantedpreonboardingbackend.post.dto.response.PostResponse;
import com.example.wantedpreonboardingbackend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.example.wantedpreonboardingbackend.common.dto.ApiResponse.success;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class PostController {

    private final PostService postService;
    private final CompanyService companyService;
    private final UserService userService;

    @PostMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> createPost(@Valid @RequestBody CreatePostRequest request) {
        int CREATE_POST = 0;

        Company company = companyService.getCompany(request.getCompanyId());
        log.info("request content : {}", request.getContent());
        int result = postService.createPost(company, request);
        log.info("postService Create result : {}", result);
        if (result == CREATE_POST) {
            return ResponseEntity.status(HttpStatus.CREATED).body(success(SuccessCode.CREATE_POST));
        } else {
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
    }

    @PutMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> updatePost(@Valid @RequestBody UpdatePostRequest request) {
        int UPDATE_POST = 0;

        int result = postService.updatePost(request);
        if (result == UPDATE_POST) {
            return ResponseEntity.status(HttpStatus.OK).body(success(SuccessCode.UPDATE_POST));
        } else {
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/post/{postId}")
    public ResponseEntity<ApiResponse<?>> deletePost(@PathVariable("postId") Long postId) {
        int DELETE_POST = 0;

        int result = postService.deletePost(postId);
        if (result == DELETE_POST) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(success(SuccessCode.DELETE_POST));
        } else {
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
    }

    @GetMapping(value = "/post/list")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts() {

        return ResponseEntity.status(HttpStatus.OK).body(success(SuccessCode.LOAD_POST, postService.getAllPosts()));
    }

    @GetMapping(value = "/post")
    public ResponseEntity<ApiResponse<List<PostResponse>>> searchAllPosts(@RequestParam("search") String search) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(success(SuccessCode.LOAD_POST, postService.searchAllPosts(search)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/post/{postId}")
    public ResponseEntity<ApiResponse<?>> getPostDetail(@PathVariable("postId") Long postId, @RequestParam("companyId") Long companyId) {

        return ResponseEntity.status(HttpStatus.OK).body(success(SuccessCode.LOAD_POST, postService.getPostDetail(postId, companyId)));
    }

}
