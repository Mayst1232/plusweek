package com.sparta.plusweek.domain.comment.controller;

import static com.sparta.plusweek.global.exception.ErrorCode.CREATE_COMMENT_FAIL;

import com.sparta.plusweek.domain.comment.dto.request.CommentRequestDto;
import com.sparta.plusweek.domain.comment.dto.response.CommentResponseDto;
import com.sparta.plusweek.domain.comment.service.CommentService;
import com.sparta.plusweek.global.dto.response.RootResponseDto;
import com.sparta.plusweek.global.exception.ServiceException;
import com.sparta.plusweek.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentRequestDto requestDto,
        BindingResult bindingResult,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            throw new ServiceException(CREATE_COMMENT_FAIL);
        }

        CommentResponseDto responseDto = commentService.createComment(requestDto.postId(),
            requestDto.content(), userDetails.getUser());

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("댓글 작성 성공")
            .data(responseDto));
    }
}
