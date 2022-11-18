package com.insta.dto.user;

import lombok.Getter;

@Getter
public class SignInResponse {
    private Long id;
    private String username;
    private String accessToken;

    protected SignInResponse() {}

    private SignInResponse(Long id, String username, String accessToken) {
        this.id = id;
        this.username = username;
        this.accessToken = accessToken;
    }

    public static SignInResponse toDto(Long id, String username, String accessToken) {
        return new SignInResponse(id, username, accessToken);
    }
}
