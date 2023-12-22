package com.sparta.plusweek.domain.post.service;

import com.sparta.plusweek.domain.post.domain.Post;
import com.sparta.plusweek.domain.post.dto.request.PostRequestDto;
import com.sparta.plusweek.domain.post.dto.response.PostResponseDto;
import com.sparta.plusweek.domain.post.repository.PostRepository;
import com.sparta.plusweek.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = Post.builder().title(requestDto.title())
            .content(requestDto.content())
            .user(user).build();

        Post savePost = postRepository.save(post);

        return PostResponseDto.builder()
            .title(savePost.getTitle())
            .content(savePost.getContent())
            .nickname(savePost.getUser().getNickname())
            .createdAt(savePost.getCreatedAt())
            .modifiedAt(savePost.getModifiedAt())
            .build();
    }

}
