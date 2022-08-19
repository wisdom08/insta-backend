package com.insta.repository;

import com.insta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

//    Optional<User> findByUsername(String username);


}
