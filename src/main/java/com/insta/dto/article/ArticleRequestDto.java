package com.insta.dto.article;

import lombok.Getter;

@Getter
public class ArticleRequestDto {
    private String content;

    protected ArticleRequestDto() {}

    public ArticleRequestDto(String content) {
        this.content = content;
    }
}
