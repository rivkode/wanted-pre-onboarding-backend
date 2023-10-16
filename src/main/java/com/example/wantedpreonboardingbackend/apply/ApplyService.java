package com.example.wantedpreonboardingbackend.apply;

import com.example.wantedpreonboardingbackend.exception.CustomException;
import com.example.wantedpreonboardingbackend.exception.ErrorCode;
import com.example.wantedpreonboardingbackend.post.dao.PostRepository;
import com.example.wantedpreonboardingbackend.post.domain.Post;
import com.example.wantedpreonboardingbackend.user.User;
import com.example.wantedpreonboardingbackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @Transactional
    public ApplyDto createApply(ApplyDto dto) {
        try {
            Long savedApplyId = applySave(dto);
            Optional<Apply> savedApply = applyRepository.findById(savedApplyId);
            return ApplyDto.from(savedApply.get());
        } catch (Exception e) {
            log.error("createApply Error {}", e.getMessage());
            throw new CustomException(ErrorCode.DATA_NOT_EXIST);
        }
    }

    public synchronized Long applySave(ApplyDto dto) {
        Optional<Apply> applyOptional = applyRepository.findByUserIdAndPostId(dto.getUserId(), dto.getPostId());

        if (applyOptional.isPresent()) {
            // 지원 실행하지 않음
            return 0L;
        } else {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST));
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_EXIST));

        Apply savedApply = applyRepository.saveAndFlush(dto.toEntity(user, post));

        return savedApply.getId();
        }
    }
}
