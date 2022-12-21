package com.insta.repository;

import com.insta.domain.Article;
import com.insta.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @DisplayName("게시글이 정상적으로 저장된다.")
    @Test
    void saveTest() {
        Article article = Article.createArticle("a", userRepository.save(User.createUser("USERNAME", "PASSWORD")));
        Article savedArticle = articleRepository.save(article);
        assertThat(savedArticle).isEqualTo(article);
    }

    @DisplayName("모든 게시글을 id 기준 내림차순으로 조회한다.")
    @Test
    void findAllOrderByIdDescTest() {
        User user = userRepository.save(User.createUser("USERNAME1", "PASSWORD1"));
        User secondUser = userRepository.save(User.createUser("USERNAME2", "PASSWORD2"));

        articleRepository.save(Article.createArticle("CONTENT1", user));
        articleRepository.save(Article.createArticle("CONTENT2", user));
        Article article3 = articleRepository.save(Article.createArticle("CONTENT3", secondUser));

        List<Article> foundArticles = articleRepository.findAllOrderByIdDesc(article3.getId(), Pageable.ofSize(999)).getContent();

        assertThat(foundArticles).hasSize(3);
        assertThat(foundArticles.get(0).getId()).isEqualTo(article3.getId());
    }

    @DisplayName("특정 유저가 작성한 게시글을 id 기준 내림차순으로 조회한다.")
    @Test
    void findAllOrderByUserDescTest() {
        User user = userRepository.save(User.createUser("USERNAME1", "PASSWORD1"));
        User secondUser = userRepository.save(User.createUser("USERNAME2", "PASSWORD2"));
        articleRepository.save(Article.createArticle("CONTENT1", user));
        Article article2 = articleRepository.save(Article.createArticle("CONTENT2", user));
        Article article3 = articleRepository.save(Article.createArticle("CONTENT3", secondUser));


        List<Article> foundArticles = articleRepository.findAllOrderByUserDesc(article3.getId(), Pageable.ofSize(999), user).getContent();
        assertThat(foundArticles).hasSize(2);
        assertThat(foundArticles.get(0).getId()).isEqualTo(article2.getId());

        List<Article> foundArticles2 = articleRepository.findAllOrderByUserDesc(article3.getId(), Pageable.ofSize(999), secondUser).getContent();
        assertThat(foundArticles2).hasSize(1);
        assertThat(foundArticles2.get(0).getId()).isEqualTo(article3.getId());
    }

}