package com.insta.dto.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class LoginRequestDto {
    private String username;
    private String password;
}
