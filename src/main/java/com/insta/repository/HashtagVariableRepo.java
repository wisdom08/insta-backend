package com.insta.repository;

import com.insta.model.Article;
import com.insta.model.HashtagVariable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagVariableRepo extends JpaRepository<HashtagVariable, Long> {
    List<HashtagVariable> findAllByArticle(Article article);
}
