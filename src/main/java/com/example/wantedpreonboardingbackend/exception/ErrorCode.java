package com.example.wantedpreonboardingbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // Common
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "서버 내부 오류"),

    // post
    INFO_NOT_EXIST(HttpStatus.BAD_REQUEST, "P001", "요청된 정보가 부족합니다"),
    DATA_NOT_EXIST(HttpStatus.BAD_REQUEST, "P002", "요청된 데이터가 존재하지 않습니다"),
    INVALID_POST(HttpStatus.BAD_REQUEST, "P003", "유효하지 않은 요청입니다"),

    // apply

    DATA_EXIST(HttpStatus.NOT_ACCEPTABLE, "a001", "이미 지원내역이 존재합니다");

    private final HttpStatus httpStatus;
    private final String type;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
