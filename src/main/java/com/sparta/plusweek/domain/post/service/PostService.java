package com.sparta.plusweek.domain.post.service;

import static com.sparta.plusweek.global.exception.ErrorCode.NOT_EXIST_POST;
import static com.sparta.plusweek.global.exception.ErrorCode.NOT_YOUR_POST;

import com.sparta.plusweek.domain.post.domain.Post;
import com.sparta.plusweek.domain.post.dto.request.PostCreateRequestDto;
import com.sparta.plusweek.domain.post.dto.response.PostResponseDto;
import com.sparta.plusweek.domain.post.repository.PostRepository;
import com.sparta.plusweek.domain.user.domain.User;
import com.sparta.plusweek.global.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostCreateRequestDto requestDto, User user) {
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

    public List<PostResponseDto> getAllPosts(Pageable pageable) {
        List<Post> postPage = postRepository.findAllBy(pageable);
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        for (Post post : postPage) {
            PostResponseDto responseDto = PostResponseDto.builder().title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }


    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
            () -> new ServiceException(NOT_EXIST_POST)
        );

        return PostResponseDto.builder()
            .title(post.getTitle())
            .content(post.getContent())
            .nickname(post.getUser().getNickname())
            .createdAt(post.getCreatedAt())
            .modifiedAt(post.getModifiedAt())
            .build();
    }


    @Transactional
    public PostResponseDto modifyPost(Long id, PostCreateRequestDto requestDto, User user) {
        Post post = postRepository.findByIdAndUser_Id(id, user.getId()).orElseThrow(
            () -> new ServiceException(NOT_YOUR_POST)
        );

        post.update(requestDto);

        return PostResponseDto.builder()
            .title(post.getTitle())
            .content(post.getContent())
            .nickname(post.getUser().getNickname())
            .createdAt(post.getCreatedAt())
            .modifiedAt(post.getModifiedAt())
            .build();
    }


    public void deletePost(Long id, User user) {
        Post post = postRepository.findByIdAndUser_Id(id, user.getId()).orElseThrow(
            () -> new ServiceException(NOT_YOUR_POST)
        );

        postRepository.delete(post);
    }
}
