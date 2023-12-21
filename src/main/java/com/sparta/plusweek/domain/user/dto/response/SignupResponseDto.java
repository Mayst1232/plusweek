package com.sparta.plusweek.domain.user.dto.response;

import lombok.Builder;

@Builder
public record SignupResponseDto(
    String username
) {

}
