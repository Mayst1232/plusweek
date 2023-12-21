package com.sparta.plusweek.domain.user.service;

import static com.sparta.plusweek.global.exception.ErrorCode.*;

import com.sparta.plusweek.domain.user.domain.User;
import com.sparta.plusweek.domain.user.dto.request.SignupRequestDto;
import com.sparta.plusweek.domain.user.dto.response.SignupResponseDto;
import com.sparta.plusweek.domain.user.repository.UserRepository;
import com.sparta.plusweek.global.exception.ServiceException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.username();
        String password = passwordEncoder.encode(requestDto.password());
        String checkPassword = requestDto.checkPassword();
        String nickname = requestDto.nickname();

        if(!passwordEncoder.matches(checkPassword, password)) {
            throw new ServiceException(VALID_PASS_INCORRECT);
        }

        if(requestDto.password().contains(nickname)) {
            throw new ServiceException(PASSWORD_NICKNAME);
        }

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new ServiceException(DUPLICATE_USERNAME);
        }

        User user = User.builder().username(username).password(password).nickname(nickname).build();
        User savedUser = userRepository.save(user);

        return SignupResponseDto.builder().username(savedUser.getUsername()).build();
    }
}
