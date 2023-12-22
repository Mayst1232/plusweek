package com.sparta.plusweek.domain.post.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PostResponseDto(
    String title,
    String content,
    String nickname,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {

}
