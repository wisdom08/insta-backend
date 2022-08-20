package com.insta.dto.article;

import lombok.Getter;

@Getter
public class ArticleRequestDto {
    private final String title;
    private final String content;

    protected void ArticleResponseDto() {}

    public ArticleRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
