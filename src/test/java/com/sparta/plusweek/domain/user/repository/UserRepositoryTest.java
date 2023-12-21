package com.sparta.plusweek.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.sparta.plusweek.domain.user.domain.User;
import com.sparta.plusweek.support.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername() {
        User user = User.builder()
            .username("test")
            .password("test1234")
            .nickname("nickname")
            .build();

        User savedUser = userRepository.save(user);

        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
            () -> new IllegalArgumentException("해당하는 사용자는 존재하지 않습니다.")
        );

        assertThat(savedUser.getUsername()).isEqualTo(findUser.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(findUser.getPassword());
    }
}