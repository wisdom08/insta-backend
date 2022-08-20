package com.insta.controller;

import com.insta.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.insta.auth.SignupRequestDto;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;



    @PostMapping("/user/signup")
    public String doSignup(@RequestBody SignupRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }





    public UserController(UserService userService) {
        this.userService = userService;
    }

}
