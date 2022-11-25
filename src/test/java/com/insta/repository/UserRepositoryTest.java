package com.insta.repository;

import com.insta.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {

    public static final String USERNAME = "wisdom";
    public static final String PASSWORD = "asdfasdf123";
    public static final String EXCEPTION_MESSAGE_FOR_WRONG_USERNAME = "wrong username";
    @Autowired
    private UserRepository userRepository;

    @Test
    void 유저_저장() {
        User user = User.createUser(USERNAME, PASSWORD);
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    void 저장된_유저_아이디로_조회() {
        User user = User.createUser(USERNAME, PASSWORD);
        userRepository.save(user);
        User findUser = userRepository.findByUsername(USERNAME).orElseThrow(() -> new IllegalArgumentException(EXCEPTION_MESSAGE_FOR_WRONG_USERNAME));
        assertThat(findUser).isEqualTo(user);
    }
}