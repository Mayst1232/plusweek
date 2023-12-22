package com.sparta.plusweek.domain.comment.repository;

import com.sparta.plusweek.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
