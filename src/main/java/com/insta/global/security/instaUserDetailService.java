package com.insta.global.security;

import com.insta.global.error.exception.EntityNotFoundException;
import com.insta.global.error.exception.ErrorCode;
import com.insta.domain.User;
import com.insta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class instaUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public instaUserDetailService() {
    }

    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new EntityNotFoundException(ErrorCode.HANDLE_ACCESS_DENIED);
        });
        return new InstaUserDetail(user);
    }

}
