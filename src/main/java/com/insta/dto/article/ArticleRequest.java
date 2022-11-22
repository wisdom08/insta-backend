package com.insta.dto.article;

import lombok.Getter;

@Getter
public class ArticleRequest {
    private String content;

    protected ArticleRequest() {}

    public ArticleRequest(String content) {
        this.content = content;
    }
}
