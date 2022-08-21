package com.insta.dto.article;

import lombok.Getter;

@Getter
public class ArticleRequestDto {
    private String title;
    private String content;

    protected ArticleRequestDto() {}

    public ArticleRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
