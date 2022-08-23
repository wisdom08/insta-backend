package com.insta.dto.user;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private Long id;
    private String username;
    private String accessToken;
    private String refreshToken;
    protected LoginResponseDto() {}

    private LoginResponseDto(Long id, String username, String accessToken, String refreshToken) {
        this.id = id;
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static LoginResponseDto toDto(Long id, String username, String accessToken, String refreshToken) {
        return new LoginResponseDto(id, username, accessToken, refreshToken);
    }
}
