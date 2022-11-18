package com.insta.service;

import com.insta.dto.article.ArticleDetailResponse;
import com.insta.dto.article.ArticleRequest;
import com.insta.dto.article.ArticleResponse;
import com.insta.dto.article.HeartResponse;
import com.insta.global.error.exception.EntityNotFoundException;
import com.insta.global.error.exception.ErrorCode;
import com.insta.global.error.exception.InvalidValueException;
import com.insta.domain.*;
import com.insta.repository.ArticleRepository;
import com.insta.repository.HashtagVariableRepository;
import com.insta.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final HeartRepository heartRepository;
    private final HashtagVariableRepository hashtagVariableRepository;
    private final S3Service s3Service;

    @Transactional
    public void createArticle(ArticleRequest requestDto, List<String> hashtagNames, MultipartFile[] articleImage) {
        User user = userService.exists(getCurrentUsername());
        Article article = articleRepository.save(Article.createArticle(requestDto.getContent(), user));

        if (articleImage != null) {
            s3Service.uploadFile(articleImage, String.valueOf(ImageTarget.ARTICLE), article.getId());
        }

        for (String name : hashtagNames) {
            if (!name.equals("false")) {
                Hashtag hashtag = Hashtag.createHashTag(name);
                hashtagVariableRepository.save(HashtagVariable.createHashtagVariable(hashtag, article));
            }
        }
    }

    @Transactional
    public void updateArticles(ArticleRequest requestDto, Long articleId, MultipartFile[] articleImage) {
        Article article = isAuthorized(articleId);
        article.updateArticle(requestDto.getContent());

        s3Service.deleteFile(ImageTarget.ARTICLE, article.getId());
        if (articleImage != null) {
            s3Service.uploadFile(articleImage, String.valueOf(ImageTarget.ARTICLE), article.getId());
        }
    }

    @Transactional
    public void deleteArticles(Long articleId) {
        isAuthorized(articleId);
        articleRepository.deleteById(articleId);
        s3Service.deleteFile(ImageTarget.ARTICLE, articleId);
    }

    private Article isAuthorized(Long articleId) {
        Article article = exists(articleId);
        User user = userService.exists(getCurrentUsername());

        if (user != article.getUser()) throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED);

        return article;
    }

    @Transactional
    public List<ArticleResponse> getArticles(Long articleId, Integer size) {
        Slice<Article> articleList = articleRepository.findAllOrderByIdDesc(articleId, Pageable.ofSize(size));
        return articleList.stream().map(entity ->
                ArticleResponse.from(entity, s3Service.getImages(ImageTarget.ARTICLE, entity.getId()))).collect(Collectors.toList());
    }

    @Transactional
    public ArticleDetailResponse getArticle(Long articleId) {
        Article article = exists(articleId);
        ArticleDetailResponse articleDetailResponse = articleRepository.findById(articleId)
                .map(entity -> ArticleDetailResponse.from(entity, s3Service.getImages(ImageTarget.ARTICLE, entity.getId())))
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_ARTICLE));

        List<HashtagVariable> hashtagVariables = hashtagVariableRepository.findAllByArticle(article);
        for (HashtagVariable hashtagVariable : hashtagVariables) {
            articleDetailResponse.addHashtag(hashtagVariable.getHashtag().getName());
        }

        return articleDetailResponse;
    }

    public Article exists(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.NOTFOUND_ARTICLE));
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }

    @Transactional
    public void toggleLike(Long articleId) {

        User user = userService.exists(getCurrentUsername());
        Article article = exists(articleId);

        heartRepository.findByUserAndArticle(user, article).ifPresentOrElse(
                heartRepository::delete
                ,
                () -> {
                    Heart heart = heartRepository.save(Heart.like(user, article));
                    article.addHearts(heart);
                });
    }

    @Transactional
    public List<ArticleResponse> getPersonalFeed(Long articleId, Integer size, String username) {
        User user = userService.exists(username);

        return articleRepository.findAllOrderByUserDesc(articleId, Pageable.ofSize(size), user)
                .stream()
                .map(entity -> ArticleResponse.from(entity, s3Service.getImages(ImageTarget.ARTICLE, entity.getId())))
                .toList();
    }

    @Transactional
    public List<HeartResponse> getArticlesLiked() {
        User user = userService.exists(getCurrentUsername());
        return user.getHaerts().stream().map(HeartResponse::from).toList();
    }

    @Transactional
    public List<ArticleResponse> findAllByHashtag(String hashtag) {
        return articleRepository.findAll().stream()
                .filter(article -> article.hasTag(hashtag))
                .map(entity -> ArticleResponse.from(entity, s3Service.getImages(ImageTarget.ARTICLE, entity.getId())))
                .collect(Collectors.toList())
                ;
    }
}
