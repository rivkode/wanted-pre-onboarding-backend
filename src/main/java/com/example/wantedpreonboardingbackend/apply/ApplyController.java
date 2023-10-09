package com.example.wantedpreonboardingbackend.apply;

import com.example.wantedpreonboardingbackend.common.dto.ApiResponse;
import com.example.wantedpreonboardingbackend.exception.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class ApplyController {
    private final ApplyService applyService;

    @PostMapping(value = "/apply", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> apply(@Valid @RequestBody ApplyDto dto) {
        applyService.createApply(dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(SuccessCode.CREATE_APPLY));
    }
}
