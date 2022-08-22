package com.insta.repository;

import com.insta.model.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepo extends JpaRepository<Article, Long>{

    @Query("select a from Article a " +
            "where a.id < :id " +
            "order by a.id desc")
    Slice<Article> findAllOrderByIdDesc(@Param("id") Long id, Pageable pageable);

}
