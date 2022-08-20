package com.insta.service;


import com.insta.auth.LoginRequestDto;
import com.insta.auth.SignupRequestDto;
import com.insta.jwt.JwtTokenProvider;
import com.insta.model.User;
import com.insta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;




    public String registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();
        String userintro = requestDto.getUserintro();

        Optional<User> userTocheck = this.userRepository.findByUsername(username);

        if(userTocheck.isPresent()){
            return "중복된 아이디 입니다.";
        }
        else if(!Objects.equals(password, passwordCheck)){
            return "비밀번호가 동일하지 않습니다.";
        }
        else if(Objects.equals(username, "")){
            return "아이디를 입력해주세요.";
        }
        else if(Objects.equals(password, "")) {
            return "비밀번호를 입력해주세요.";
        }
        else {
            password = this.passwordEncoder.encode(password);
            requestDto.setPassword(password);
            User user = new User(username, password);
            this.userRepository.save(user);
            return "회원가입에 성공하였습니다.";
        }

    }
    public Boolean login(LoginRequestDto loginRequestDto) {
        User user = this.userRepository.findByUsername(loginRequestDto.getUsername()).orElse((User) null);
        if(user != null) {
            return this.passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        }
        else return false;
    }










}
