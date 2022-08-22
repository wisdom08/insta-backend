package com.insta.controller;

import com.insta.dto.auth.LoginRequestDto;
import com.insta.dto.auth.SignupRequestDto;
import com.insta.jwt.JwtTokenProvider;
import com.insta.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/signin")
    public String doLogin(@RequestBody LoginRequestDto loginRequestDto){
        if(userService.login(loginRequestDto)) {
            String token = this.jwtTokenProvider.createToken(loginRequestDto.getUsername());
            System.out.println(token);
            return token;
        }
        else return "아이디, 비밀번호를 확인해주세요.";
    }



    @PostMapping("/signup")
    public String doSignup(@RequestBody SignupRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }





    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

}
