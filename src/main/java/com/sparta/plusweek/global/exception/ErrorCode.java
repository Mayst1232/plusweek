package com.sparta.plusweek.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "1000", "로그인에 실패했습니다."),
    SIGNUP_FAILED(HttpStatus.BAD_REQUEST, "1001", "회원가입에 실패했습니다."),
    PASSWORD_NICKNAME(HttpStatus.BAD_REQUEST, "1002", "비밀번호에 닉네임이 들어갈 수 없습니다."),
    VALID_PASS_INCORRECT(HttpStatus.BAD_REQUEST, "1003", "비밀번호와 확인 비밀번호가 다릅니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "1004", "중복된 유저 이름 입니다."),

    CREATE_POST_FAIL(HttpStatus.BAD_REQUEST, "2000", "게시글 작성에 실패했습니다."),
    NOT_EXIST_POST(HttpStatus.BAD_REQUEST, "2001", "해당 ID의 게시글이 존재하지 않습니다."),
    NOT_YOUR_POST(HttpStatus.BAD_REQUEST, "2002", "자신의 게시글만 수정 / 삭제가 가능합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}