package com.insta.repository;

import com.insta.model.Article;
import com.insta.model.Comment;
import com.insta.model.Heart;
import com.insta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepo extends JpaRepository<Heart, Long> {
    Optional<Heart> findByUserAndArticle(User user, Article article);
    Optional<Heart> findByUserAndComment(User user, Comment comment);
}
