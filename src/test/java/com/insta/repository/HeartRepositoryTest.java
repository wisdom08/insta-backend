package com.insta.repository;

import com.insta.domain.Article;
import com.insta.domain.Comment;
import com.insta.domain.Heart;
import com.insta.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class HeartRepositoryTest {

    private User user;
    private User secondUser;
    private Article article;
    private Article secondArticle;
    private Comment comment;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private HeartRepository heartRepository;

    @Autowired
    private CommentRepository commentRepository;


    @BeforeEach
    void setUp() {
        user = userRepository.save(User.createUser("USERNAME1", "PASSWORD1"));
        secondUser = userRepository.save(User.createUser("USERNAME2", "PASSWORD2"));

        article = articleRepository.save(Article.createArticle("CONTENT1", user));
        secondArticle = articleRepository.save(Article.createArticle("CONTENT2", secondUser));

        comment = commentRepository.save(Comment.createComment(article, "COMMENT1", user));
    }

    @DisplayName("특정 유저와 특정 게시글의 좋아요를 조회합니다.")
    @Test
    void findByUserAndArticleTest() {
        Heart heart = Heart.like(user, article);
        heartRepository.save(heart);

        Optional<Heart> foundHeart = heartRepository.findByUserAndArticle(user, article);
        assertThat(foundHeart).isNotNull();
        assertThat(foundHeart.get().getUser()).isEqualTo(user);
        assertThat(foundHeart.get().getArticle()).isEqualTo(article);

        Heart secondHeart = Heart.like(secondUser, secondArticle);
        heartRepository.save(secondHeart);

        Optional<Heart> foundSecondHeart = heartRepository.findByUserAndArticle(secondUser, secondArticle);
        assertThat(foundSecondHeart).isNotNull();
        assertThat(foundSecondHeart.get().getUser()).isEqualTo(secondUser);
        assertThat(foundSecondHeart.get().getArticle()).isEqualTo(secondArticle);
    }

    @DisplayName("특정 유저와 특정 댓글의 좋아요를 조회합니다.")
    @Test
    void findByUserAndCommentTes() {
        Heart heart = Heart.like(user, comment);
        heartRepository.save(heart);

        Optional<Heart> foundHeart = heartRepository.findByUserAndComment(user, comment);
        assertThat(foundHeart).isNotNull();
        assertThat(foundHeart.get().getUser()).isEqualTo(user);
        assertThat(foundHeart.get().getComment()).isEqualTo(comment);

        Heart secondHeart = Heart.like(secondUser, comment);
        heartRepository.save(secondHeart);

        Optional<Heart> foundSecondHeart = heartRepository.findByUserAndComment(secondUser, comment);
        assertThat(foundSecondHeart).isNotNull();
        assertThat(foundSecondHeart.get().getUser()).isEqualTo(secondUser);
        assertThat(foundSecondHeart.get().getComment()).isEqualTo(comment);
    }
}