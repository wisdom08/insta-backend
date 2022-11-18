package com.insta.dto.comment;

import com.insta.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private int numberOfReply;
    private int numberOfLikes;


    protected CommentResponse() {}

    private CommentResponse(Long id, String content, int numberOfLikes, LocalDateTime createdAt, int numberOfReply) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.numberOfReply = numberOfReply;
        this.numberOfLikes = numberOfLikes;
    }

    public static CommentResponse from(Comment entity) {
        return new CommentResponse(entity.getId(), entity.getContent(), entity.getHearts().size(), entity.getCreatedAt(), entity.getReplies().size());
    }
}
