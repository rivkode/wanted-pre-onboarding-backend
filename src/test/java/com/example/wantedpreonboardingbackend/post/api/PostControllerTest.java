package com.example.wantedpreonboardingbackend.post.api;


import static org.mockito.ArgumentMatchers.any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.example.wantedpreonboardingbackend.company.Company;
import com.example.wantedpreonboardingbackend.company.Scale;
import com.example.wantedpreonboardingbackend.post.application.PostService;
import com.example.wantedpreonboardingbackend.post.dto.request.CreatePostRequest;
import com.example.wantedpreonboardingbackend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(PostController.class)
@AutoConfigureWebMvc
public class PostControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    private static final Logger log = LoggerFactory.getLogger(PostControllerTest.class);

    @MockBean
    PostService postService;

    @MockBean
    PostController postController;

    User user100;
    Company company100;

    private final int CREATE_POST = 0;

    @BeforeEach
    void init() {
        user100 = new User(100L, "이종훈", "spring", "백엔드");
        company100 = new Company(100L, "wanted", Scale.STARTUP);
    }

//    @AfterEach
//    void exit() {
//        userRepository.delete(user100);
//        companyRepository.delete(company100);
//    }

    // http://localhost:8080/api/v1/post
    @Test
    @DisplayName("Post 단건 생성")
    void createPostTest() throws Exception {
        CreatePostRequest request = new CreatePostRequest("백엔드 개발자 모집합니다", "백엔드 주니어 개발자", 10000,
                "Spring", "한국", "서울", 100L);
        Gson gson = new Gson();
        String content = gson.toJson(request);
        log.info(">> content 내용 : {}", content);
        log.info(">> request : {}", request);


        // when
        Mockito.when(postService.createPost(any(Company.class), any(CreatePostRequest.class))).thenReturn(CREATE_POST);

        // then
        // mockMvc : restApi 테스트를 할 수 있는 환경 제공
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/post")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.message").exists())
                .andDo(print());

        verify(postService, times(1)).createPost(company100, request);
    }
}
