package com.sparta.plusweek.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "1000", "로그인에 실패했습니다."),
    SIGNUP_FAILED(HttpStatus.BAD_REQUEST, "1001", "회원가입에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}