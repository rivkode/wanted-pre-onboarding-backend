package com.example.wantedpreonboardingbackend.apply;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private ApplyRepository applyRepository;

    private final Long TEST_USER_ID = 1L;
    private final Long TEST_POST_ID = 8L;

    private final int COMPLETE = 0;

    @Test
    @DisplayName("Apply 지원 하기")
    void createApply() {
        //given
        ApplyDto dto = new ApplyDto(TEST_USER_ID, TEST_POST_ID);

        //when
        int count = applyRepository.findAll().size();

        int result = applyService.createApply(dto);

        //then
        assertThat(result).isEqualTo(COMPLETE);

        assertThat(applyRepository.findAll()).hasSize(count + 1);
    }
}