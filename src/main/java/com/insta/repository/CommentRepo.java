package com.insta.repository;

import com.insta.model.Article;
import com.insta.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findAllByArticleAndParent(Article article, Comment parent);

    List<Comment> findAllByParent(Comment comment);
}
