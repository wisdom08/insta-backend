package com.insta.controller;

import com.insta.dto.article.ArticleDetailResponse;
import com.insta.dto.article.ArticleRequest;
import com.insta.dto.article.ArticleResponse;
import com.insta.dto.article.HeartResponse;
import com.insta.global.response.ResponseUtil;
import com.insta.global.response.CommonResponse;
import com.insta.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Api(tags = "게시글 API")
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private ArticleService articleService;

    @ApiOperation(value = "게시글 전체 조회")
    @GetMapping()
    public CommonResponse<List<ArticleResponse>> getArticles(@RequestParam Long articleId, @RequestParam Integer size) {
        List<ArticleResponse> articles = articleService.getArticles(articleId, size);
        return ResponseUtil.success(200, articles);
    }

    @ApiOperation(value = "특정 게시글 조회")
    @GetMapping("/{articleId}")
    public CommonResponse<ArticleDetailResponse> getArticle(@PathVariable Long articleId) {
        return ResponseUtil.success(200, articleService.getArticle(articleId));
    }

    @ApiOperation(value = "게시글 등록")
    @PostMapping
    public CommonResponse<?> createArticles(ArticleRequest requestDto, MultipartFile[] articleImage,
                                            @RequestParam(value = "hashtags", defaultValue = "false") List<String> hashtags){
        articleService.createArticle(requestDto, hashtags, articleImage);
        return ResponseUtil.success(201, null);
    }

    @ApiOperation(value = "게시글 수정")
    @PutMapping("/{articleId}")
    public CommonResponse<?> updateArticles(@PathVariable Long articleId, ArticleRequest requestDto, MultipartFile[] articleImage) {
        articleService.updateArticles(requestDto, articleId, articleImage);
        return ResponseUtil.success(200, null);
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/{articleId}")
    public CommonResponse<?> deleteArticles(@PathVariable Long articleId) {
        articleService.deleteArticles(articleId);
        return ResponseUtil.success(200, null);
    }

    @ApiOperation(value = "게시글 좋아요")
    @PostMapping("/{articleId}/likes")
    public CommonResponse<?> likeArticle(@PathVariable Long articleId) {
        articleService.toggleLike(articleId);
        return ResponseUtil.success(200, null);
    }

    @ApiOperation(value = "각 유저가 등록한 글 전체 조회")
    @GetMapping("/feed/{username}")
    public CommonResponse<List<ArticleResponse>> getPersonalFeed(@RequestParam Long articleId, @RequestParam Integer size, @PathVariable String username) {
        return ResponseUtil.success(200, articleService.getPersonalFeed(articleId, size, username));
    }

    @ApiOperation(value = "로그인한 유저가 좋아요 한 글 조회")
    @GetMapping("/likes")
    public CommonResponse<List<HeartResponse>> getArticlesLiked() {
        return ResponseUtil.success(200, articleService.getArticlesLiked());
    }

    @ApiOperation(value = "해시태그 기준 검색 조회")
    @GetMapping("/search")
    public CommonResponse<List<ArticleResponse>> getFilteredPosts(@RequestParam String hashtag) {
        return ResponseUtil.success(200, articleService.findAllByHashtag(hashtag));
    }
}