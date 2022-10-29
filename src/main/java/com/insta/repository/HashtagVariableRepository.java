package com.insta.repository;

import com.insta.domain.Article;
import com.insta.domain.HashtagVariable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagVariableRepository extends JpaRepository<HashtagVariable, Long> {
    List<HashtagVariable> findAllByArticle(Article article);
}
