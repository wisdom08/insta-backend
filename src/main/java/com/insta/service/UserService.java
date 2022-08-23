package com.insta.service;


import com.insta.dto.user.LoginRequestDto;
import com.insta.dto.user.LoginResponseDto;
import com.insta.dto.user.SignupRequestDto;
import com.insta.global.error.exception.EntityNotFoundException;
import com.insta.global.error.exception.ErrorCode;
import com.insta.global.error.exception.InvalidValueException;
import com.insta.jwt.JwtTokenProvider;
import com.insta.model.User;
import com.insta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void registerUser(SignupRequestDto signupRequestDto, Errors errors) {
        validateUser(signupRequestDto, errors);
        User user = User.createUser(signupRequestDto.getUsername(), passwordEncoder.encode(signupRequestDto.getPassword()));
        userRepository.save(user);
    }

    public HashMap<Object, Object> login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        User user = exists(username);
        validatePassword(loginRequestDto.getPassword(), user.getPassword());

        return makeLoginMap(username, user);
    }

    private HashMap<Object, Object> makeLoginMap(String username, User user) {
        String accessToken = jwtTokenProvider.createToken(username);
        String refreshToken = jwtTokenProvider.createRefreshToken();

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken",refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60)
                .build();

        HashMap<Object, Object> map = new HashMap<>();
        LoginResponseDto loginResponseDto = LoginResponseDto.toDto(user.getId(), user.getUsername(), accessToken);
        map.put("loginResponseDto", loginResponseDto);
        map.put("responseCookie", responseCookie);
        return map;
    }

    private void validatePassword(String inputPassword, String savedPassword) {
        boolean passwordMatching = passwordEncoder.matches(inputPassword, savedPassword);
        if(!passwordMatching) throw new InvalidValueException(ErrorCode.LOGIN_INPUT_INVALID);
    }

    private void validateUser(SignupRequestDto requestDto, Errors errors) {
        userRepository.findByUsername(requestDto.getUsername())
                .ifPresent(user -> {
                    throw new InvalidValueException(ErrorCode.USERNAME_DUPLICATION);
                });

        isSamePassword(requestDto);

        if (errors.hasErrors()) {
            throw new InvalidValueException(ErrorCode.INVALID_USER_INPUT);
        }
    }

    private void isSamePassword(SignupRequestDto requestDto) {
        if (!(requestDto.getPassword().equals(requestDto.getPasswordCheck())))
            throw new InvalidValueException(ErrorCode.NOT_EQUAL_INPUT_PASSWORD);
    }


    public User exists(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.NOTFOUND_USER));
    }
}
