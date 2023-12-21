package com.sparta.plusweek.domain.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparta.plusweek.domain.user.dto.request.SignupRequestDto;
import com.sparta.plusweek.domain.user.service.UserService;
import com.sparta.plusweek.support.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@DisplayName("유저 API 단위 테스트")
public class UserControllerTest extends ControllerTest {

    @MockBean
    UserService userService;

    @Nested
    @DisplayName("유저 회원 가입")
    class SignupUser {

        @DisplayName("성공 케이스")
        @Test
        void success() throws Exception {
            SignupRequestDto requestDto = new SignupRequestDto("hwang1234", "qwer1234", "qwer1234", "Mayst");
            String json = objectMapper.writeValueAsString(requestDto);

            mockMvc.perform(post("/api/user/signup")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.code").value("200"),
                    jsonPath("$.message").value("회원가입 완료")
                );
        }

        @DisplayName("실패 케이스")
        void fail() throws Exception {
            SignupRequestDto requestDto = new SignupRequestDto("hwang1234", "qwe", "qwer1234", "Mayst");
            String json = objectMapper.writeValueAsString(requestDto);

            mockMvc.perform(post("/api/user/signup")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.code").value("1001"),
                    jsonPath("$.message").value("회원가입에 실패했습니다.")
                );
        }
    }
}
