package com.sparta.plusweek.domain.post.repository;

import com.sparta.plusweek.domain.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBy(Pageable pageable);

    Optional<Post> findByIdAndUser_Id(Long id, Long id1);
}
