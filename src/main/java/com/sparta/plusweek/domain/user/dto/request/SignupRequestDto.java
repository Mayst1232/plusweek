package com.sparta.plusweek.domain.user.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignupRequestDto(
    @Pattern(regexp = "^[a-z0-9]*$")
    @Size(min = 3)
    String username,
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Size(min = 4)
    String password,
    @NotBlank
    String checkPassword,
    @NotBlank
    String nickname
) {

}
