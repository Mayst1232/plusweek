package com.sparta.plusweek.domain.comment.dto.request;

import lombok.Builder;

@Builder
public record CommentModifyRequestDto(
    String content
) {

}
