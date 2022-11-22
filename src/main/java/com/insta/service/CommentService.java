package com.insta.service;

import com.insta.dto.comment.CommentRequest;
import com.insta.dto.comment.CommentResponse;
import com.insta.dto.comment.ReplyResponse;
import com.insta.global.error.exception.EntityNotFoundException;
import com.insta.global.error.exception.ErrorCode;
import com.insta.global.error.exception.InvalidValueException;
import com.insta.domain.Article;
import com.insta.domain.Comment;
import com.insta.domain.Heart;
import com.insta.domain.User;
import com.insta.repository.CommentRepository;
import com.insta.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;
    private final UserService userService;
    private final HeartRepository heartRepository;

    public void createComment(Long articleId, CommentRequest commentRequest) {
        User user = userService.exists(getCurrentUsername());
        Article article = articleService.exists(articleId);
        commentRepository.save(Comment.createComment(article, commentRequest.getContent(), user));
    }

    @Transactional
    public List<CommentResponse> getComments(Long articleId, Long commentId, Integer size) {
        Article article = articleService.exists(articleId);
        Slice<Comment> commentList = commentRepository.findAllOrderByIdDesc(commentId, article, Pageable.ofSize(size));
        return commentList.stream().map(CommentResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReplyResponse> getReplies(Long articleId, Long commentId, Long replyId, Integer size) {
        articleService.exists(articleId);
        Comment comment = exists(commentId);
        Slice<Comment> replyList = commentRepository.findAllRepliesByParent(comment, replyId, Pageable.ofSize(size));
        return replyList.stream().map(ReplyResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long articleId, Long commentId) {
        articleService.exists(articleId);
        isAuthorized(commentId);

        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void deleteReply(Long articleId, Long commentId, Long replyId) {
        articleService.exists(articleId);
        exists(commentId);
        isAuthorized(replyId);

        commentRepository.deleteById(replyId);
    }

    private void isAuthorized(Long id) {
        Comment comment = exists(id);
        User user = userService.exists(getCurrentUsername());
        if(user != comment.getUser()) throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED);
    }

    public void createReply(Long articleId, Long commentId, CommentRequest commentRequest) {
        User user = userService.exists(getCurrentUsername());
        Article article = articleService.exists(articleId);
        Comment comment = exists(commentId);
        commentRepository.save(Comment.createReply(article, comment, commentRequest.getContent(), user));
    }

    public Comment exists(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_COMMENT));
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails)principal).getUsername();
    }

    @Transactional
    public void toggleLike(Long articleId, Long commentId) {
        articleService.exists(articleId);
        User user = userService.exists(getCurrentUsername());
        Comment comment = exists(commentId);

        heartRepository.findByUserAndComment(user, comment).ifPresentOrElse(
                heartRepository::delete
                ,
                () -> {
                    Heart heart = heartRepository.save(Heart.like(user, comment));
                    comment.addHearts(heart);
                });
    }
}
