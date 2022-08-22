package com.insta.controller;

import com.insta.dto.user.LoginRequestDto;
import com.insta.dto.user.LoginResponseDto;
import com.insta.dto.user.SignupRequestDto;
import com.insta.global.response.ApiUtils;
import com.insta.global.response.CommonResponse;
import com.insta.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "유저 API")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "로그인")
    @PostMapping("/signin")
    public CommonResponse<?> doLogin(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
        return ApiUtils.success(200, loginResponseDto);
    }

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public CommonResponse<?> doSignup(@Valid @RequestBody SignupRequestDto signupRequestDto, Errors errors) {
        userService.registerUser(signupRequestDto, errors);
        return ApiUtils.success(201, null);
    }
}
