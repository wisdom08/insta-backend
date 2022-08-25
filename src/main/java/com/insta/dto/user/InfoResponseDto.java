package com.insta.dto.user;

import lombok.Getter;

@Getter
public class InfoResponseDto {
    private String username;
    private String bio;
    private int numberOfArticles;
    private String image;

    protected InfoResponseDto() {}

    private InfoResponseDto(String username, String bio, int numberOfArticles, String image) {
        this.username = username;
        this.bio = bio;
        this.numberOfArticles = numberOfArticles;
        this.image = image;
    }

    public static InfoResponseDto makeInfoDto(String username, String bio, int numberOfArticles, String image) {
        return new InfoResponseDto(username, bio, numberOfArticles, image);
    }
}
