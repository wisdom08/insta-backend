package com.insta.service;

import com.insta.dto.article.ArticleDetailResponseDto;
import com.insta.dto.article.ArticleRequestDto;
import com.insta.dto.article.ArticleResponseDto;
import com.insta.global.error.exception.ErrorCode;
import com.insta.global.error.exception.EntityNotFoundException;
import com.insta.model.Article;
import com.insta.repository.ArticleRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;

    public ArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public void createArticle (ArticleRequestDto requestDto){
        articleRepo.save(Article.createArticle(requestDto.getTitle(), requestDto.getContent()));
    }

    @Transactional
    public void updateArticles(ArticleRequestDto requestDto, Long articleId){
        Article article = exists(articleId);
        article.updateArticle(requestDto.getTitle(), requestDto.getContent());
    }

    public void deleteArticles(Long articleId){
        exists(articleId);
        articleRepo.deleteById(articleId);
    }

    @Transactional
    public List<ArticleResponseDto> getArticles() {
        List<Article> articleList = articleRepo.findAll();
        return articleList.stream().map(ArticleResponseDto::from).collect(Collectors.toList());
    }

    public ArticleDetailResponseDto getArticle(Long articleId) {
        return articleRepo.findById(articleId)
                .map(ArticleDetailResponseDto::from)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_ARTICLE));
    }

    public Article exists(Long articleId) {
        return articleRepo.findById(articleId).orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.NOTFOUND_ARTICLE));
    }
}
