package com.insta.repository;

import com.insta.domain.Article;
import com.insta.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long>{

    @Query("select a from Article a " +
            "where a.id <= :id " +
            "order by a.id desc")
    Slice<Article> findAllOrderByIdDesc(@Param("id") Long id, Pageable pageable);

    @Query("select a from Article a " +
            "where a.id <= :id and a.user = :user " +
            "order by a.id desc")
    Slice<Article> findAllOrderByUserDesc(@Param("id") Long id, Pageable pageable, @Param("user") User user);
}
