package com.insta.dto.user;

import lombok.Getter;

@Getter
public class InfoResponse {
    private String username;
    private String bio;
    private int numberOfArticles;
    private String image;

    protected InfoResponse() {}

    private InfoResponse(String username, String bio, int numberOfArticles, String image) {
        this.username = username;
        this.bio = bio;
        this.numberOfArticles = numberOfArticles;
        this.image = image;
    }

    public static InfoResponse makeInfoDto(String username, String bio, int numberOfArticles, String image) {
        return new InfoResponse(username, bio, numberOfArticles, image);
    }
}
