package com.insta.controller;

import com.insta.dto.article.ArticleDetailResponseDto;
import com.insta.dto.article.ArticleRequestDto;
import com.insta.dto.article.ArticleResponseDto;
import com.insta.global.response.ApiUtils;
import com.insta.global.response.CommonResponse;
import com.insta.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Api(tags = "게시글 API")
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @ApiOperation(value = "게시글 전체 조회")
    @GetMapping()
    public CommonResponse<List<ArticleResponseDto>> getArticles(@RequestParam Long articleId, @RequestParam Integer size) {
        List<ArticleResponseDto> articles = articleService.getArticles(articleId, size);
        return ApiUtils.success(200, articles);
    }

    @ApiOperation(value = "특정 게시글 조회")
    @GetMapping("/{articleId}")
    public CommonResponse<ArticleDetailResponseDto> getArticle(@PathVariable Long articleId) {
        return ApiUtils.success(200, articleService.getArticle(articleId));
    }

    @ApiOperation(value = "게시글 등록")
    @PostMapping
    public CommonResponse<?> createArticles(@RequestBody ArticleRequestDto requestDto){
        articleService.createArticle(requestDto);
        return ApiUtils.success(201, null);
    }

    @ApiOperation(value = "게시글 수정")
    @PutMapping("/{articleId}")
    public CommonResponse<?> updateArticles(@PathVariable Long articleId, @RequestBody ArticleRequestDto requestDto) {
        articleService.updateArticles(requestDto, articleId);
        return ApiUtils.success(200, null);
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/{articleId}")
    public CommonResponse<?> deleteArticles(@PathVariable Long articleId) {
        articleService.deleteArticles(articleId);
        return ApiUtils.success(200, null);
    }

}