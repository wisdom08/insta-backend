package com.insta.repository;

import com.insta.domain.Article;
import com.insta.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @DisplayName("게시글이 정상적으로 저장된다.")
    @Test
    void saveArticleTest() {
        assertThat(savedArticle).isEqualTo(article);
    }

    @DisplayName("게시글이 내림차순으로 조회된다.")
    @Test
    void findAllOrderByIdDescTest() {
        article = Article.createArticle("CONTENT1", user);
        savedArticle = articleRepository.save(article);
        secondSavedArticle = articleRepository.save(Article.createArticle("CONTENT2", user));
        thirdSavedArticle = articleRepository.save(Article.createArticle("CONTENT3", secondUser));

        List<Article> articles = articleRepository.findAllOrderByIdDesc(savedArticle.getId(), Pageable.ofSize(999)).getContent();

        assertThat(articles.get(0).getContent()).isEqualTo("CONTENT3");
    }

    @DisplayName("게시글이 유저별로 내림차순 조회된다.")
    @Test
    void findAllOrderByUserDescTest() {
        article = Article.createArticle("CONTENT1", user);
        savedArticle = articleRepository.save(article);
        secondSavedArticle = articleRepository.save(Article.createArticle("CONTENT2", user));
        thirdSavedArticle = articleRepository.save(Article.createArticle("CONTENT3", secondUser));

        List<Article> articles = articleRepository.findAllOrderByUserDesc(savedArticle.getId(), Pageable.ofSize(999), user).getContent();

        assertThat(articles.get(0).getContent()).isEqualTo("CONTENT2");
    }

}