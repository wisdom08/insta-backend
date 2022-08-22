package com.insta.dto.user;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String username;
    private String password;

    protected LoginRequestDto() {}
}
