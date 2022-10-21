package com.insta.repository;

import com.insta.domain.Article;
import com.insta.domain.Comment;
import com.insta.domain.Heart;
import com.insta.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepo extends JpaRepository<Heart, Long> {
    Optional<Heart> findByUserAndArticle(User user, Article article);
    Optional<Heart> findByUserAndComment(User user, Comment comment);
}
