package com.sparta.plusweek.domain.user.dto.response;

import lombok.Builder;

@Builder
public record LoginResponseDto(
    String username
) {

}
