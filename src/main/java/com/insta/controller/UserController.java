package com.insta.controller;

import com.insta.dto.user.InfoRequest;
import com.insta.dto.user.InfoResponse;
import com.insta.dto.user.SignInRequest;
import com.insta.dto.user.SignUpRequest;
import com.insta.global.response.CommonResponse;
import com.insta.global.response.ResponseUtil;
import com.insta.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@Api(tags = "유저 API")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<CommonResponse<Object>> signIn(@RequestBody SignInRequest signinRequest){
        Map<Object, Object> map = userService.signIn(signinRequest);

        Object loginResponseDto = map.get("loginResponseDto");
        ResponseCookie responseCookie = (ResponseCookie)map.get("responseCookie");

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(
            ResponseUtil.success(200, loginResponseDto));
    }

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public CommonResponse<CommonResponse> signUp(@Valid @RequestBody SignUpRequest signupRequest, Errors errors) {
        userService.signUp(signupRequest, errors);
        return ResponseUtil.success(201, null);
    }

    @ApiOperation(value = "유저 정보 조회")
    @GetMapping("/{username}")
    public CommonResponse<InfoResponse> getInfo(@PathVariable String username) {
        return ResponseUtil.success(200, userService.getInfo(username));
    }

    @ApiOperation(value = "프로필사진/bio 수정")
    @PutMapping("/accounts")
    public CommonResponse<CommonResponse> updateInfo(InfoRequest infoRequest, MultipartFile[] image) {
        userService.updateInfo(infoRequest, image);
        return ResponseUtil.success(200, null);
    }
}
