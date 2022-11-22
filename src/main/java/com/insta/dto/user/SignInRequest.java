package com.insta.dto.user;

import lombok.Getter;

@Getter
public class SignInRequest {
    private String username;
    private String password;

    protected SignInRequest() {}
}
