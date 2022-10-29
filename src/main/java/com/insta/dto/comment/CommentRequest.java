package com.insta.dto.comment;

import lombok.Getter;

@Getter
public class CommentRequest {
    private String content;

    protected CommentRequest() {}
}
