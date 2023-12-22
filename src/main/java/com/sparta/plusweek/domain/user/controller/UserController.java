package com.sparta.plusweek.domain.user.controller;

import static com.sparta.plusweek.global.exception.ErrorCode.SIGNUP_FAILED;

import com.sparta.plusweek.domain.user.dto.request.SignupRequestDto;
import com.sparta.plusweek.domain.user.dto.response.SignupResponseDto;
import com.sparta.plusweek.domain.user.service.UserService;
import com.sparta.plusweek.global.dto.response.RootResponseDto;
import com.sparta.plusweek.global.exception.ServiceException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto requestDto,
        BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            throw new ServiceException(SIGNUP_FAILED);
        }

        SignupResponseDto responseDto = userService.signup(requestDto);

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("회원가입 완료")
            .data(responseDto)
            .build());
    }
}
