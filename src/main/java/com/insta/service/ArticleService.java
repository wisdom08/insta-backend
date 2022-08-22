package com.insta.service;

import com.insta.dto.article.ArticleDetailResponseDto;
import com.insta.dto.article.ArticleRequestDto;
import com.insta.dto.article.ArticleResponseDto;
import com.insta.global.error.exception.EntityNotFoundException;
import com.insta.global.error.exception.ErrorCode;
import com.insta.global.error.exception.InvalidValueException;
import com.insta.model.Article;
import com.insta.model.User;
import com.insta.repository.ArticleRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;
    private final UserService userService;

    public ArticleService(ArticleRepo articleRepo, UserService userService) {
        this.articleRepo = articleRepo;
        this.userService = userService;
    }

    public void createArticle (ArticleRequestDto requestDto){
        User user = userService.exists(getCurrentUsername());
        articleRepo.save(Article.createArticle(requestDto.getContent(), user));
    }

    @Transactional
    public void updateArticles(ArticleRequestDto requestDto, Long articleId){
        Article article = isAuthorized(articleId);
        article.updateArticle(requestDto.getContent());
    }
    @Transactional
    public void deleteArticles(Long articleId){
        isAuthorized(articleId);
        articleRepo.deleteById(articleId);
    }

    private Article isAuthorized(Long articleId) {
        Article article = exists(articleId);
        User user = userService.exists(getCurrentUsername());

        if (user != article.getUser()) throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED);

        return article;
    }

    @Transactional
    public List<ArticleResponseDto> getArticles(Long articleId, Integer size) {
        Slice<Article> articleList = articleRepo.findAllOrderByIdDesc(articleId, Pageable.ofSize(size));
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

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails)principal).getUsername();
    }
}
