package com.sparta.plusweek.domain.post.controller;

import static com.sparta.plusweek.global.exception.ErrorCode.CREATE_POST_FAIL;

import com.sparta.plusweek.domain.post.dto.request.PostRequestDto;
import com.sparta.plusweek.domain.post.dto.response.PostResponseDto;
import com.sparta.plusweek.domain.post.service.PostService;
import com.sparta.plusweek.global.dto.response.RootResponseDto;
import com.sparta.plusweek.global.exception.ServiceException;
import com.sparta.plusweek.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostRequestDto requestDto,
        BindingResult bindingResult,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            throw new ServiceException(CREATE_POST_FAIL);
        }
        PostResponseDto responseDto = postService.createPost(requestDto, userDetails.getUser());

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("게시물이 생성됐습니다.")
            .data(responseDto)
            .build());
    }


}
