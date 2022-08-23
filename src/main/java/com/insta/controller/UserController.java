package com.insta.controller;

import com.insta.dto.user.LoginRequestDto;
import com.insta.dto.user.LoginResponseDto;
import com.insta.dto.user.SignupRequestDto;
import com.insta.global.response.ApiUtils;
import com.insta.global.response.CommonResponse;
import com.insta.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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

//    @ApiOperation(value = "토큰 재발급", notes = "토큰을 재발급한다")
//    @PostMapping(value = "/refresh")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access-token", required = true, dataType = "String", paramType = "header"),
//            @ApiImplicitParam(name = "REFRESH-TOKEN", value = "refresh-token", required = true, dataType = "String", paramType = "header")
//    })
//    public SingleResult<LoginResponseDto> refreshToken(
//            @RequestHeader(value="X-AUTH-TOKEN") String token,
//            @RequestHeader(value="REFRESH-TOKEN") String refreshToken ) {
//        return responseService.handleSingleResult(signService.refreshToken(token, refreshToken));
//    }

}
