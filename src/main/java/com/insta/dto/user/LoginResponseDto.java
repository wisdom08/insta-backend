package com.insta.dto.user;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private Long id;
    private String username;
    private String accessToken;

    protected LoginResponseDto() {}

    private LoginResponseDto(Long id, String username, String accessToken) {
        this.id = id;
        this.username = username;
        this.accessToken = accessToken;
    }

    public static LoginResponseDto toDto(Long id, String username, String accessToken) {
        return new LoginResponseDto(id, username, accessToken);
    }
}
