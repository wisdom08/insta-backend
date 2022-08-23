package com.insta.controller;

import com.insta.dto.user.InfoResponseDto;
import com.insta.dto.user.LoginRequestDto;
import com.insta.dto.user.SignupRequestDto;
import com.insta.global.response.ApiUtils;
import com.insta.global.response.CommonResponse;
import com.insta.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@Api(tags = "유저 API")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<CommonResponse<Object>> doLogin(@RequestBody LoginRequestDto loginRequestDto){
        HashMap<Object, Object> map = userService.login(loginRequestDto);

        Object loginResponseDto = map.get("loginResponseDto");
        ResponseCookie responseCookie = (ResponseCookie)map.get("responseCookie");

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(ApiUtils.success(200, loginResponseDto));
    }

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public CommonResponse<?> doSignup(@Valid @RequestBody SignupRequestDto signupRequestDto, Errors errors) {
        userService.registerUser(signupRequestDto, errors);
        return ApiUtils.success(201, null);
    }

    @ApiOperation(value = "유저 정보 조회")
    @GetMapping("/{username}")
    public CommonResponse<InfoResponseDto> getInfo(@PathVariable String username) {
        return ApiUtils.success(200, userService.getInfo(username));
    }

}
