package com.insta.dto.comment;

import com.insta.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private int numberOfLikes;

    protected ReplyResponse() {}

    private ReplyResponse(Long id, String content, int numberOfLikes, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.numberOfLikes = numberOfLikes;
    }

    public static ReplyResponse from(Comment entity) {
        return new ReplyResponse(entity.getId(), entity.getContent(), entity.getHearts().size(), entity.getCreatedAt());
    }
}
