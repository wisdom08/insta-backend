package com.insta.repository;

import com.insta.domain.Article;
import com.insta.domain.Hashtag;
import com.insta.domain.HashtagVariable;
import com.insta.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class HashtagVariableRepositoryTest {

    private Article article;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashtagVariableRepository hashtagVariableRepository;

    @BeforeEach
    void setUp() {
        article = articleRepository.save(
                Article.createArticle("article1",
                        userRepository.save(User.createUser("user1", "pw1"))));
    }


    @DisplayName("특정 게시글에 등록된 해시태그를 모두 조회합니다.")
    @Test
    void findAllByArticleTest() {
        Hashtag hashtag = Hashtag.createHashTag("hashtag");
        HashtagVariable savedHashtag = hashtagVariableRepository.save(HashtagVariable.createHashtagVariable(hashtag, article));

        assertThat(savedHashtag.getArticle()).isEqualTo(article);
        assertThat(savedHashtag.getHashtag()).isEqualTo(hashtag);
    }
}