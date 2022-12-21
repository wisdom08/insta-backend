package com.insta.dto.comment;

import lombok.Getter;

@Getter
public class CommentRequest {
    private String content;

    protected CommentRequest() {}

    public CommentRequest(String content) {
        this.content = content;
    }
}
