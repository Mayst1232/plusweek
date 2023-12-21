package com.sparta.plusweek.domain.user.dto.request;

import lombok.Builder;

@Builder
public record LoginRequestDto(
    String username,
    String password
) {

}
