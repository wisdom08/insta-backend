package com.insta.dto.user;

import lombok.Getter;

@Getter
public class InfoResponseDto {
    private String username;
    private String userintro;
    private int numberOfArticles;

    protected InfoResponseDto() {}

    private InfoResponseDto(String username, String userintro, int numberOfArticles) {
        this.username = username;
        this.userintro = userintro;
        this.numberOfArticles = numberOfArticles;
    }

    public static InfoResponseDto makeInfoDto(String username, String userintro, int numberOfArticles) {
        return new InfoResponseDto(username, userintro, numberOfArticles);
    }
}
