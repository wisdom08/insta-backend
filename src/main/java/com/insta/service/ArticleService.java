package com.insta.service;

import com.insta.dto.article.ArticleDetailResponseDto;
import com.insta.dto.article.ArticleRequestDto;
import com.insta.dto.article.ArticleResponseDto;
import com.insta.dto.article.LikesResponseDto;
import com.insta.global.error.exception.EntityNotFoundException;
import com.insta.global.error.exception.ErrorCode;
import com.insta.global.error.exception.InvalidValueException;
import com.insta.domain.*;
import com.insta.repository.ArticleRepo;
import com.insta.repository.HashtagVariableRepo;
import com.insta.repository.LikeRepo;
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
    private final ArticleRepo articleRepo;
    private final UserService userService;
    private final LikeRepo likeRepo;
    private final HashtagVariableRepo hashtagVariableRepo;
    private final S3Service s3Service;

    @Transactional
    public void createArticle(ArticleRequestDto requestDto, List<String> hashtagNames, MultipartFile[] articleImage) {
        User user = userService.exists(getCurrentUsername());
        Article article = articleRepo.save(Article.createArticle(requestDto.getContent(), user));

        if (articleImage != null) {
            s3Service.uploadFile(articleImage, String.valueOf(ImageTarget.ARTICLE), article.getId());
        }

        for (String name : hashtagNames) {
            if (!name.equals("false")) {
                Hashtag hashtag = Hashtag.createHashTag(name);
                hashtagVariableRepo.save(HashtagVariable.createHashtagVariable(hashtag, article));
            }
        }
    }

    @Transactional
    public void updateArticles(ArticleRequestDto requestDto, Long articleId, MultipartFile[] articleImage) {
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
        articleRepo.deleteById(articleId);
        s3Service.deleteFile(ImageTarget.ARTICLE, articleId);
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
        return articleList.stream().map(entity ->
                ArticleResponseDto.from(entity, s3Service.getImages(ImageTarget.ARTICLE, entity.getId()))).collect(Collectors.toList());
    }

    @Transactional
    public ArticleDetailResponseDto getArticle(Long articleId) {
        Article article = exists(articleId);
        ArticleDetailResponseDto articleDetailResponseDto = articleRepo.findById(articleId)
                .map(entity -> ArticleDetailResponseDto.from(entity, s3Service.getImages(ImageTarget.ARTICLE, entity.getId())))
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_ARTICLE));

        List<HashtagVariable> hashtagVariables = hashtagVariableRepo.findAllByArticle(article);
        for (HashtagVariable hashtagVariable : hashtagVariables) {
            articleDetailResponseDto.addHashtag(hashtagVariable.getHashtag().getName());
        }

        return articleDetailResponseDto;
    }

    public Article exists(Long articleId) {
        return articleRepo.findById(articleId).orElseThrow(() ->
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

        likeRepo.findByUserAndArticle(user, article).ifPresentOrElse(
                likeRepo::delete
                ,
                () -> {
                    Heart heart = likeRepo.save(Heart.Like(user, article));
                    article.addHearts(heart);
                });
    }

    @Transactional
    public List<ArticleResponseDto> getPersonalFeed(Long articleId, Integer size, String username) {
        User user = userService.exists(username);

        return articleRepo.findAllOrderByUserDesc(articleId, Pageable.ofSize(size), user)
                .stream()
                .map(entity -> ArticleResponseDto.from(entity, s3Service.getImages(ImageTarget.ARTICLE, entity.getId())))
                .toList();
    }

    @Transactional
    public List<LikesResponseDto> getArticlesLiked() {
        User user = userService.exists(getCurrentUsername());
        return user.getHaerts().stream().map(LikesResponseDto::from).toList();
    }

    @Transactional
    public List<ArticleResponseDto> findAllByHashtag(String hashtag) {
        return articleRepo.findAll().stream()
                .filter(article -> article.hasTag(hashtag))
                .map(entity -> ArticleResponseDto.from(entity, s3Service.getImages(ImageTarget.ARTICLE, entity.getId())))
                .collect(Collectors.toList())
                ;
    }
}
