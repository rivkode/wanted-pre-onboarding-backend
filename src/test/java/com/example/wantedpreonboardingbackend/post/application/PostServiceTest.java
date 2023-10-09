package com.example.wantedpreonboardingbackend.post.application;

import com.example.wantedpreonboardingbackend.company.Company;
import com.example.wantedpreonboardingbackend.company.Scale;
import com.example.wantedpreonboardingbackend.post.dao.PostRepository;
import com.example.wantedpreonboardingbackend.post.domain.Post;
import com.example.wantedpreonboardingbackend.post.dto.request.CreatePostRequest;
import com.example.wantedpreonboardingbackend.post.dto.request.UpdatePostRequest;
import com.example.wantedpreonboardingbackend.post.dto.response.PostResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    private final String TEST_CONTENT = "원티드랩에서 채용공고를 시행";
    private final String TEST_POSITION = "백엔드 주니어 개발자";
    private final int TEST_REWARD = 1000000;
    private final String TEST_SKILLS = "python";
    private final String TEST_COUNTRY = "한국";
    private final String TEST_REGION = "서울";
    private final Long TEST_COMPANY_ID = 1L;
    private final Long TEST_POST_ID = 1L;
    private final int COMPLETE = 0;

    @BeforeEach
    void setUp() {
//        Company company = new Company(1L, "wanted", Scale.STARTUP);
    }

    @Test
    @DisplayName("Post 공고 저장")
    void createPost() {
        //given
        Company company = new Company(1L, "원티드", Scale.STARTUP);
        CreatePostRequest request = new CreatePostRequest(
                TEST_CONTENT, TEST_POSITION, TEST_REWARD, TEST_SKILLS, TEST_COUNTRY,
                TEST_REGION, TEST_COMPANY_ID
        );

        //when
        int count = postRepository.findAll().size();

        int result = postService.createPost(company, request);

        //then
        assertThat(result).isEqualTo(COMPLETE);

        assertThat(postRepository.findAll()).hasSize(count + 1);
    }


    @Test
    @DisplayName("Post 공고 수정")
    void updatePost() {
        //given
        String updateContent = "수정된 내용";
        UpdatePostRequest request = new UpdatePostRequest(updateContent, TEST_POSITION, TEST_REWARD, TEST_SKILLS, TEST_COUNTRY,
                TEST_REGION, TEST_COMPANY_ID, TEST_POST_ID
        );

        //when
        int result = postService.updatePost(request);
        Optional<Post> updatedPost = postRepository.findById(request.getPostId());

        //then
        assertThat(result).isEqualTo(COMPLETE);

        assertThat(updatedPost.get().getContent()).isEqualTo(updateContent);
    }

    @Test
    @DisplayName("Post Detail 의 companyIds 는 1 이상이다")
    void getPostDetail() {
        //given
        Long postId = 1L;
        Long companyId = 1L;

        //when
        PostResponse postResponseDetail = postService.getPostDetail(postId, companyId);

        //then
        // 자기 자신을 포함한 postId가 1이상이어야 한다
        assertThat(postResponseDetail.getCompanyIds()).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("Post의 개수는 1개 이상이다")
    void getAllPosts() {
        //given

        //when
        List<PostResponse> postResponseList = postService.getAllPosts();

        //then
        assertThat(postResponseList).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("Post 를 keyword로 검색하는 경우")
    void searchAllPosts() {
        //given
        String keyword = "wanted";

        //when
        List<PostResponse> postResponseList = postService.searchAllPosts(keyword);

        //then
        // companyName을 검색하는 경우
        assertThat(postResponseList.stream()
                .anyMatch(postResponse -> postResponse.getCompanyName().contains(keyword))).isTrue();
    }
}