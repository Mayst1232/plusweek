package com.sparta.plusweek.domain.comment.service;

import static com.sparta.plusweek.global.exception.ErrorCode.NOT_EXIST_POST;

import com.sparta.plusweek.domain.comment.domain.Comment;
import com.sparta.plusweek.domain.comment.dto.response.CommentResponseDto;
import com.sparta.plusweek.domain.comment.repository.CommentRepository;
import com.sparta.plusweek.domain.post.domain.Post;
import com.sparta.plusweek.domain.post.repository.PostRepository;
import com.sparta.plusweek.domain.user.domain.User;
import com.sparta.plusweek.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponseDto createComment(Long postId, String content, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
            () -> new ServiceException(NOT_EXIST_POST)
        );

        Comment comment = Comment.builder()
            .content(content)
            .post(post)
            .user(user)
            .build();

        Comment saveComment = commentRepository.save(comment);

        return CommentResponseDto.builder()
            .nickname(saveComment.getUser().getNickname())
            .content(saveComment.getContent())
            .createdAt(saveComment.getCreatedAt())
            .modifiedAt(saveComment.getModifiedAt())
            .build();
    }
}