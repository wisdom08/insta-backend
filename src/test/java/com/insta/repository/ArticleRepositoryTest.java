package com.insta.repository;

import com.insta.domain.Article;
import com.insta.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ArticleRepositoryTest {

    private User user;
    private User secondUser;
    private Article article;
    private Article savedArticle;
    private Article secondSavedArticle;
    private Article thirdSavedArticle;
    private UserRepository userRepository;
    private ArticleRepository articleRepository;

    @Autowired
    ArticleRepositoryTest(UserRepository userRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.createUser("USERNAME", "PASSWORD"));
        secondUser = userRepository.save(User.createUser("USERNAME2", "PASSWORD2"));

        article = Article.createArticle("CONTENT1", user);
        savedArticle = articleRepository.save(article);

        secondSavedArticle = articleRepository.save(Article.createArticle("CONTENT2", user));
        thirdSavedArticle = articleRepository.save(Article.createArticle("CONTENT3", secondUser));
    }

    @Test
    void 게시글_저장() {
        assertThat(savedArticle).isEqualTo(article);
    }

    @Test
    void 게시글_조회_내림차순() {
        article = Article.createArticle("CONTENT1", user);
        savedArticle = articleRepository.save(article);
        secondSavedArticle = articleRepository.save(Article.createArticle("CONTENT2", user));
        thirdSavedArticle = articleRepository.save(Article.createArticle("CONTENT3", secondUser));

        List<Article> articles = articleRepository.findAllOrderByIdDesc(savedArticle.getId(), Pageable.ofSize(999)).getContent();

        assertThat(articles.get(0).getContent()).isEqualTo("CONTENT3");
    }

    @Test
    void 게시글_조회_유저별_내림차순() {
        article = Article.createArticle("CONTENT1", user);
        savedArticle = articleRepository.save(article);
        secondSavedArticle = articleRepository.save(Article.createArticle("CONTENT2", user));
        thirdSavedArticle = articleRepository.save(Article.createArticle("CONTENT3", secondUser));

        List<Article> articles = articleRepository.findAllOrderByUserDesc(savedArticle.getId(), Pageable.ofSize(999), user).getContent();

        assertThat(articles.get(0).getContent()).isEqualTo("CONTENT2");
    }

}