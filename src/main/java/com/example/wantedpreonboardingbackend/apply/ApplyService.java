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

        Optional<Apply> applyOptional = applyRepository.findByUserIdAndPostId(dto.getUserId(), dto.getPostId());

        if (applyOptional.isPresent()) {
            throw new CustomException(ErrorCode.DATA_EXIST);

        } else {
            Optional<User> userOptional = userRepository.findById(dto.getUserId());
            log.info(">> userId : {}", dto.getUserId());
            Optional<Post> postOptional = postRepository.findById(dto.getPostId());
            log.info(">> postId : {}", dto.getPostId());
            Apply savedApply = applyRepository.save(dto.toEntity(userOptional.get(), postOptional.get()));

            return ApplyDto.from(savedApply);
        }
    }
}
