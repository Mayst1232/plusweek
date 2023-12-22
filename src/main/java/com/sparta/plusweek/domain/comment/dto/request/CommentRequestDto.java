package com.sparta.plusweek.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CommentRequestDto(

    @NotBlank
    Long postId,
    @NotBlank
    @Size(max = 500)
    String content
) {

}
