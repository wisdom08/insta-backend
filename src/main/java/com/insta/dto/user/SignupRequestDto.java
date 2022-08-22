package com.insta.dto.user;


import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    @Pattern(regexp = "^[a-z0-9_-]{3,16}$")
    @NotBlank
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;

    protected SignupRequestDto() {}
}
