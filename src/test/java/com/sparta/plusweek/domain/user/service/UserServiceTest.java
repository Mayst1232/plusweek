package com.sparta.plusweek.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sparta.plusweek.domain.user.domain.User;
import com.sparta.plusweek.domain.user.dto.request.SignupRequestDto;
import com.sparta.plusweek.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Nested
    @DisplayName("회원가입 서비스 API 테스트")
    class SignUpTest {
        @Test
        void success() {
            // given
            SignupRequestDto requestDto = SignupRequestDto.builder().username("hwang1234")
                .password("qwer1234").checkPassword("qwer1234")
                .nickname("kyujeong").build();
            User user = User.builder().username(requestDto.username()).build();
            given(passwordEncoder.encode(any()))
                .willReturn(new BCryptPasswordEncoder().encode(requestDto.password()));
            given(passwordEncoder.matches(any(), any())).willReturn(true);
            given(userRepository.findByUsername(any())).willReturn(Optional.empty());
            given(userRepository.save(any())).willReturn(user);

            // when
            userService.signup(requestDto);

            // then
            verify(userRepository, times(1)).save(any());
        }
    }
}