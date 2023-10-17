package com.example.wantedpreonboardingbackend.post.api;


import static org.mockito.ArgumentMatchers.any;

import com.example.wantedpreonboardingbackend.post.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.example.wantedpreonboardingbackend.company.Company;
import com.example.wantedpreonboardingbackend.company.Scale;
import com.example.wantedpreonboardingbackend.post.application.PostService;
import com.example.wantedpreonboardingbackend.post.dto.request.CreatePostRequest;
import com.example.wantedpreonboardingbackend.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {
    /**
     * mockmvc 오류로 인해 MockHttpServletResponse 가 올바른 예상하는 Response 를 보내지 않아 테스트 작성 실패
     */

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    private static final Logger log = LoggerFactory.getLogger(PostControllerTest.class);

    @MockBean
    PostService postService;

    @MockBean
    PostController postController;

//    User user100;
//    Company company100;

    private final int CREATE_POST = 0;
//
//    @BeforeEach
//    void init() {
//        user100 = new User(100L, "이종훈", "spring", "백엔드");
//        company100 = new Company(100L, "wanted", Scale.STARTUP);
//    }

//    @AfterEach
//    void exit() {
//        userRepository.delete(user100);
//        companyRepository.delete(company100);
//    }

    // http://localhost:8080/api/v1/post
    @Test
    @DisplayName("Post 단건 생성")
    void createPostTest() throws Exception {
        User user100 = new User(100L, "이종훈", "spring", "백엔드");
        Company company100 = new Company(100L, "wanted", Scale.STARTUP);
        CreatePostRequest request = new CreatePostRequest("백엔드 개발자 모집합니다", Position.데브옵스개발자, 10000,
                Skills.MYSQL, Country.미국, Region.도쿄, 100L);
        Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = gson.toJson(request);
        String content1 = objectMapper.writeValueAsString(request);
        log.info(">> content 내용 : {}", content1);
        log.info(">> request : {}", request);
        Post post = request.toEntity(company100);

//        given(postService.createPost(any(Company.class), any(CreatePostRequest.class))).willReturn(CREATE_POST);

        log.info(">> after service given");


        // when
//        when(postService.createPost(any(Company.class), any(CreatePostRequest.class))).thenReturn(CREATE_POST);

        log.info(">> after service when");

        // then
        // mockMvc : restApi 테스트를 할 수 있는 환경 제공
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/post")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content1))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.message").exists())
                .andDo(print())
                .andReturn();

        log.info(">> after perform");

        verify(postService, times(1)).createPost(company100, request);
    }

    @Test
    @DisplayName("Post update")
    void updatePost() {

    }

    @Test
    @DisplayName("Post delete")
    void deletePost() {

    }

    @Test
    @DisplayName("Get Post List")
    void getAllPosts() {

    }

    @Test
    @DisplayName("Search Post List")
    void searchAllPosts() {

    }

    @Test
    @DisplayName("get Post Detail")
    void getPostDetail() {

    }
}
