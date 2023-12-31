package com.sparta.plusweek.domain.post.controller;

import static com.sparta.plusweek.global.exception.ErrorCode.CREATE_POST_FAIL;

import com.sparta.plusweek.domain.post.dto.request.PostCreateRequestDto;
import com.sparta.plusweek.domain.post.dto.response.PostResponseDto;
import com.sparta.plusweek.domain.post.service.PostService;
import com.sparta.plusweek.global.dto.PageDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<?> createPost(@RequestBody @Valid PostCreateRequestDto requestDto,
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

    @GetMapping("/post")
    public ResponseEntity<?> getAllPosts(@RequestBody PageDTO pageDTO) {
        List<PostResponseDto> response = postService.getAllPosts(pageDTO.toPageable());
        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("모든 게시물을 조회합니다.")
            .data(response)
            .build());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        PostResponseDto responseDto = postService.getPost(id);
        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message(id + "번의 게시물을 조회합니다.")
            .data(responseDto)
            .build());
    }


    @PatchMapping("/post/{id}")
    public ResponseEntity<?> modifyPost(@PathVariable Long id,
        @RequestBody PostCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto responseDto = postService.modifyPost(id, requestDto, userDetails.getUser());

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("게시물 수정 성공")
            .data(responseDto)
            .build());
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails.getUser());

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("게시물 삭제 성공")
            .build());
    }
}
