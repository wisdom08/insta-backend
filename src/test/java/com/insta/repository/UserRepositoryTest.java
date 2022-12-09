package com.insta.repository;

import com.insta.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {

    private static final String USERNAME = "wisdom";
    private static final String PASSWORD = "asdfasdf123";
    private User user;
    private User savedUser;

    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        user = User.createUser(USERNAME, PASSWORD);
        savedUser = userRepository.save(user);
    }

    @Test
    void 유저_저장() {
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    void 저장된_유저_아이디로_조회() {
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
    }
}