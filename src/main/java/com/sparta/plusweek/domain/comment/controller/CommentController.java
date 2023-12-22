package com.sparta.plusweek.domain.comment.controller;

import static com.sparta.plusweek.global.exception.ErrorCode.CREATE_COMMENT_FAIL;

import com.sparta.plusweek.domain.comment.dto.request.CommentCreateRequestDto;
import com.sparta.plusweek.domain.comment.dto.request.CommentModifyRequestDto;
import com.sparta.plusweek.domain.comment.dto.response.CommentResponseDto;
import com.sparta.plusweek.domain.comment.service.CommentService;
import com.sparta.plusweek.global.dto.PageDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentCreateRequestDto requestDto,
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
            .data(responseDto)
            .build());
    }

    @GetMapping("/comment/post/{id}")
    public ResponseEntity<?> getAllComments(@RequestBody PageDTO pageDTO,
        @PathVariable Long id) {
        List<CommentResponseDto> responseDtoList = commentService.getAllComments(id,
            pageDTO.toPageable());

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("댓글 페이징 조회")
            .data(responseDtoList)
            .build());
    }

    @PatchMapping("/comment/{id}")
    public ResponseEntity<?> modifyComment(@PathVariable Long id,
        @RequestBody CommentModifyRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.modifyComment(id, requestDto,
            userDetails.getUser());

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("댓글 수정 성공")
            .data(responseDto)
            .build());
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(id, userDetails.getUser());

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("댓글 삭제 성공")
            .build());
    }
}
