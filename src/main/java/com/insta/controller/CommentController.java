package com.insta.controller;

import com.insta.dto.comment.CommentRequest;
import com.insta.dto.comment.CommentResponse;
import com.insta.dto.comment.ReplyResponse;
import com.insta.global.response.ResponseUtil;
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
    public CommonResponse<CommonResponse> createComment(@PathVariable Long articleId, @RequestBody CommentRequest commentRequest) {
        commentService.createComment(articleId, commentRequest);
        return ResponseUtil.success(201, null);
    }

    @ApiOperation(value = "대댓글 등록")
    @PostMapping("/comments/{commentId}/replies")
    public CommonResponse<CommonResponse> createReply(@PathVariable Long articleId, @PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        commentService.createReply(articleId, commentId, commentRequest);
        return ResponseUtil.success(201, null);
    }

    @ApiOperation(value = "특정 게시글의 전체 댓글 조회")
    @GetMapping("/comments")
    public CommonResponse<List<CommentResponse>> getComments(@PathVariable Long articleId, @RequestParam Long commentId, @RequestParam Integer size) {
        return ResponseUtil.success(200, commentService.getComments(articleId, commentId, size));
    }

    @ApiOperation(value = "특정 댓글의 대댓글 전체 조회")
    @GetMapping("/comments/{commentId}/replies")
    public CommonResponse<List<ReplyResponse>> getReplies(@PathVariable Long articleId, @PathVariable Long commentId, @RequestParam Long replyId, @RequestParam Integer size) {
        return ResponseUtil.success(200, commentService.getReplies(articleId, commentId, replyId, size));
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/comments/{commentId}")
    public CommonResponse<?> deleteComment(@PathVariable Long articleId, @PathVariable Long commentId) {
        commentService.deleteComment(articleId, commentId);
        return ResponseUtil.success(200, null);
    }

    @ApiOperation(value = "대댓글 삭제")
    @DeleteMapping("/comments/{commentId}/replies/{replyId}")
    public CommonResponse<CommonResponse> deleteReply(@PathVariable Long articleId, @PathVariable Long commentId, @PathVariable Long replyId) {
        commentService.deleteReply(articleId, commentId, replyId);
        return ResponseUtil.success(200, null);
    }

    @ApiOperation(value = "댓글/대댓글 좋아요")
    @PostMapping("/comments/{commentId}/likes")
    public CommonResponse<CommonResponse> likeComment(@PathVariable Long commentId, @PathVariable Long articleId) {
        commentService.toggleLike(articleId, commentId);
        return ResponseUtil.success(200, null);
    }
}
