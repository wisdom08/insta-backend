package com.insta.service;

import com.insta.dto.comment.CommentRequestDto;
import com.insta.dto.comment.CommentResponseDto;
import com.insta.dto.comment.ReplyResponseDto;
import com.insta.global.error.exception.EntityNotFoundException;
import com.insta.global.error.exception.ErrorCode;
import com.insta.model.Article;
import com.insta.model.Comment;
import com.insta.repository.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepo commentRepo;
    private final ArticleService articleService;

    public void createComment(Long articleId, CommentRequestDto commentRequestDto) {
        Article article = articleService.exists(articleId);
        commentRepo.save(Comment.createComment(article, commentRequestDto.getContent()));
    }

    @Transactional
    public List<CommentResponseDto> getComments(Long articleId) {
        Article article = articleService.exists(articleId);
        List<Comment> commentList = commentRepo.findAllByArticleAndParent(article, null);
        return commentList.stream().map(CommentResponseDto::from)
                .collect(Collectors.toList());
    }

    public List<ReplyResponseDto> getReplies(Long articleId, Long commentId) {
        articleService.exists(articleId);
        Comment comment = exists(commentId);
        List<Comment> replyList = commentRepo.findAllByParent(comment);
        return replyList.stream().map(ReplyResponseDto::from)
                .collect(Collectors.toList());
    }


    public void deleteComment(Long articleId, Long commentId) {
        articleService.exists(articleId);
        commentRepo.deleteById(commentId);
    }

    public void deleteReply(Long articleId, Long commentId, Long replyId) {
        articleService.exists(articleId);
        exists(commentId);
        commentRepo.deleteById(replyId);
    }

    public void createReply(Long articleId, Long commentId, CommentRequestDto commentRequestDto) {
        Article article = articleService.exists(articleId);
        Comment comment = exists(commentId);
        commentRepo.save(Comment.createReply(article, comment, commentRequestDto.getContent()));
    }

    public Comment exists(Long commentId) {
        return commentRepo.findById(commentId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_COMMENT));
    }
}
