package com.sparta.plusweek.domain.comment.repository;

import com.sparta.plusweek.domain.comment.domain.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost_Id(Long postId, Pageable pageable);


    Optional<Comment> findByIdAndUser_id(Long commentId, Long userId);
}
