package com.insta.repository;

import com.insta.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Articles, Long>{

    List<Articles> findAllByOrderByCreatedAtDesc();



}
