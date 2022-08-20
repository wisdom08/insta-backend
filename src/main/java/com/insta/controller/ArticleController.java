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

    //일정 수정
    @PutMapping("/api/articles/{article_Id}")
    public String updateArticles(@PathVariable Long article_Id, @RequestBody ArticleRequestDto requestDto) {
        return articleService.updateArticles(requestDto, article_Id);
    }

    //일정 삭제
    @DeleteMapping("/api/articles/{article_Id}")
    public String deleteArticles(@PathVariable Long article_Id) {
        return articleService.deleteArticles(article_Id);

    }


}