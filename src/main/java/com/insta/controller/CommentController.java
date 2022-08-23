package com.insta.controller;

import com.insta.dto.comment.CommentRequestDto;
import com.insta.dto.comment.CommentResponseDto;
import com.insta.dto.comment.ReplyResponseDto;
import com.insta.global.response.ApiUtils;
import com.insta.global.response.CommonResponse;
import com.insta.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Api(tags = "댓글/대댓글 API")
@RequestMapping("/api/articles/{articleId}")
@RestController
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 등록")
    @PostMapping
    public CommonResponse<?> createComment(@PathVariable Long articleId, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.createComment(articleId, commentRequestDto);
        return ApiUtils.success(201, null);
    }

    @ApiOperation(value = "대댓글 등록")
    @PostMapping("/comments/{commentId}/replies")
    public CommonResponse<?> createReply(@PathVariable Long articleId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.createReply(articleId, commentId, commentRequestDto);
        return ApiUtils.success(201, null);
    }

    @ApiOperation(value = "특정 게시글의 전체 댓글 조회")
    @GetMapping("/comments")
    public CommonResponse<List<CommentResponseDto>> getComments(@PathVariable Long articleId, @RequestParam Long commentId, @RequestParam Integer size) {
        return ApiUtils.success(200, commentService.getComments(articleId, commentId, size));
    }

    @ApiOperation(value = "특정 댓글의 대댓글 전체 조회")
    @GetMapping("/comments/{commentId}/replies")
    public CommonResponse<List<ReplyResponseDto>> getReplies(@PathVariable Long articleId, @PathVariable Long commentId, @RequestParam Long replyId, @RequestParam Integer size) {
        return ApiUtils.success(200, commentService.getReplies(articleId, commentId, replyId, size));
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/comments/{commentId}")
    public CommonResponse<?> deleteComment(@PathVariable Long articleId, @PathVariable Long commentId) {
        commentService.deleteComment(articleId, commentId);
        return ApiUtils.success(200, null);
    }

    @ApiOperation(value = "대댓글 삭제")
    @DeleteMapping("/comments/{commentId}/replies/{replyId}")
    public CommonResponse<?> deleteReply(@PathVariable Long articleId, @PathVariable Long commentId, @PathVariable Long replyId) {
        commentService.deleteReply(articleId, commentId, replyId);
        return ApiUtils.success(200, null);
    }

    @ApiOperation(value = "댓글/대댓글 좋아요")
    @PostMapping("/comments/{commentId}/likes")
    public CommonResponse<?> likeComment(@PathVariable Long commentId, @PathVariable Long articleId) {
        commentService.toggleLike(articleId, commentId);
        return ApiUtils.success(200, null);
    }
}
