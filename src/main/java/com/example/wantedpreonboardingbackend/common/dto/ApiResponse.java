package com.example.wantedpreonboardingbackend.common.dto;

import com.example.wantedpreonboardingbackend.exception.ErrorCode;
import com.example.wantedpreonboardingbackend.exception.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ApiResponse<T> {
    private final int code;
    private final String message;
    private T data;

    public static ApiResponse<?> success(SuccessCode successCode) {
        return new ApiResponse<>(successCode.getCode(), successCode.getMessage());
    }

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<T>(successCode.getCode(), successCode.getMessage(), data);
    }

    public static ApiResponse<?> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getHttpStatus().value(), errorCode.getMessage());
    }


}
