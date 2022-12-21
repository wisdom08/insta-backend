package com.insta.repository;

import com.insta.domain.Article;
import com.insta.domain.Comment;
import com.insta.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class CommentRepositoryTest {

    private User user;
    private Article article;
    private Article secondArticle;
    private Comment comment;
    private Comment reply;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.createUser("user1", "pw1"));
        article = articleRepository.save(Article.createArticle("article1", user));
        secondArticle = articleRepository.save(Article.createArticle("article2", user));
        comment = commentRepository.save(Comment.createComment(article, "comment1", user));
        reply = commentRepository.save(Comment.createReply(article, comment, "reply1", user));
    }


    @DisplayName("댓글이 정상적으로 저장된다.")
    @Test
    void saveTest() {
        Comment comment = Comment.createComment(article, "comment2", user);
        Comment savedSecondComment = commentRepository.save(comment);

        assertAll(
                () -> assertThat(savedSecondComment).isNotNull(),
                () -> assertThat(savedSecondComment).isEqualTo(comment)
        );
    }


    @DisplayName("하나의 게시글에 있는 모든 댓글을 id 기준 내림차순으로 조회한다.")
    @Test
    void findAllOrderByIdDescTest() {
        Comment savedSecondComment = commentRepository.save(Comment.createComment(article, "comment2", user));
        commentRepository.save(Comment.createComment(secondArticle, "comment3", user));

        List<Comment> comments = commentRepository.findAllOrderByIdDesc(savedSecondComment.getId(), article, Pageable.ofSize(999)).getContent();
        assertThat(comments).hasSize(2);
        assertThat(comments.get(0).getId()).isEqualTo(savedSecondComment.getId());
    }

    @DisplayName("하나의 게시글에 있는 특정 댓글의 대댓글을 id 기준 내림차순으로 조회한다.")
    @Test
    void findAllRepliesByParentTest() {
        Comment savedSecondReply = commentRepository.save(Comment.createReply(article, comment, "reply2", user));

        List<Comment> replies = commentRepository.findAllRepliesByParent(comment, savedSecondReply.getId(), Pageable.ofSize(999)).getContent();

        assertThat(replies).hasSize(2);
        assertThat(replies.get(0).getId()).isEqualTo(savedSecondReply.getId());
    }
}