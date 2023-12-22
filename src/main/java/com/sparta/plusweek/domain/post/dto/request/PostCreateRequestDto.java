package com.sparta.plusweek.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PostCreateRequestDto(
    @NotBlank
    @Size(max = 500)
    String title,
    @NotBlank
    @Size(max = 5000)
    String content
) {


}
