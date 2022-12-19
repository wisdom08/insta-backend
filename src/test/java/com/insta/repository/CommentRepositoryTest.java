package com.insta.repository;

import com.insta.domain.Article;
import com.insta.domain.Comment;
import com.insta.domain.User;
import com.insta.dto.comment.CommentRequest;
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

    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;
    private UserRepository userRepository;
    private Comment comment;
    private Comment secondComment;
    private Comment savedReply;
    private Comment savedSecondReply;
    private Article article;
    private User user;

    @Autowired
    public CommentRepositoryTest(CommentRepository commentRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        user = User.createUser("user1", "pw1");
        article = Article.createArticle("article1", user);
        articleRepository.save(article);
        userRepository.save(user);

        comment = commentRepository.save(Comment.createComment(article, "comment1", user));
        secondComment = commentRepository.save(Comment.createComment(article, "comment2", user));
    }

    @DisplayName("댓글이 정상적으로 저장된다.")
    @Test
    void saveTest() {
        assertAll(
                () -> assertThat(comment).isNotNull(),
                () -> assertThat(comment.getContent()).isEqualTo("comment1")
        );
    }

    @DisplayName("하나의 게시글에 있는 모든 댓글을 보여준다.")
    @Test
    void findAllTest() {
        List<Comment> comments = commentRepository.findByArticle(article);
        assertThat(comments).hasSize(2);
    }

    @DisplayName("하나의 게시글에 있는 모든 댓글을 내림차순으로 정렬해서 보여준다.")
    @Test
    void findAllOrderByIdDescTest() {
        Comment savedComment = commentRepository.save(Comment.createComment(article, "content1", user));

        List<Comment> comments = commentRepository.findAllOrderByIdDesc(savedComment.getId(), article, Pageable.ofSize(999)).getContent();
        assertThat(comments).hasSize(2);
        assertThat(comments.get(0).getContent()).isEqualTo("comment2");
    }

    @DisplayName("하나의 게시글에 있는 특정 댓글의 대댓글을 내림차순으로 정렬해서 보여준다.")
    @Test
    void findAllRepliesByParentTest() {

        CommentRequest commentRequest = new CommentRequest("reply1");
        CommentRequest commentSecondRequest = new CommentRequest("reply2");

        savedReply = commentRepository.save(Comment.createReply(article, comment, commentRequest.getContent(), user));
        savedSecondReply = commentRepository.save(Comment.createReply(article, comment, commentSecondRequest.getContent(), user));

        List<Comment> replies = commentRepository.findAllRepliesByParent(comment, savedSecondReply.getId(), Pageable.ofSize(999)).getContent();

        assertThat(replies).hasSize(2);
        assertThat(replies.get(0).getContent()).isEqualTo("reply2");
    }
}