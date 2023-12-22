package com.sparta.plusweek.domain.comment.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CommentResponseDto(
    String nickname,
    String content,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {

}
