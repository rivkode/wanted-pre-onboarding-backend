package com.example.wantedpreonboardingbackend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum SuccessCode {

    // post
    /**
     * 200 OK status
     */
    LOAD_POST(HttpStatus.OK.value(), "공고 목록을 불러오는데 성공하였습니다"),

    UPDATE_POST(HttpStatus.OK.value(), "공고 수정이 성공하였습니다"),


    /**
     * 201 CREATED status
     */
    CREATE_POST(HttpStatus.CREATED.value(), "공고 생성에 성공하였습니다"),

    /**
     * 204 NO_CONTENT status
     */
    DELETE_POST(HttpStatus.NO_CONTENT.value(), "공고 삭제에 성공하였습니다"),

    // apply
    CREATE_APPLY(HttpStatus.CREATED.value(), "지원에 성공하였습니다");




    private final int code;
    private final String message;
}
