package com.sparta.plusweek.domain.post.domain;

import com.sparta.plusweek.domain.comment.domain.Comment;
import com.sparta.plusweek.domain.model.BaseEntity;
import com.sparta.plusweek.domain.post.dto.request.PostRequestDto;
import com.sparta.plusweek.domain.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class Post extends BaseEntity {

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(PostRequestDto requestDto) {
        if (requestDto.content() != null && requestDto.title() != null) {
            this.title = requestDto.title();
            this.content = requestDto.content();
        } else if (requestDto.title() == null) {
            this.content = requestDto.content();
        } else {
            this.title = requestDto.title();
        }
    }
}
