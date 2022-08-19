package com.insta.controller;

import com.insta.model.Articles;
import com.insta.repository.ArticleRepository;
import com.insta.dto.article.ArticleRequestDto;
import com.insta.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    //게시글 조회
    @GetMapping("/api/articles")
    public List<Articles> getArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }

    //게시글 작성
    @PostMapping("/api/articles")
    public String createArticles(@RequestBody ArticleRequestDto requestDto){
        return articleService.createArticles(requestDto);
    }



}