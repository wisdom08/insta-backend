package com.insta.dto.comment;

import com.insta.model.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private int numberOfReply;

    protected CommentResponseDto() {}

    private CommentResponseDto(Long id, String content, LocalDateTime createdAt, int numberOfReply) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.numberOfReply = numberOfReply;
    }

    public static CommentResponseDto from(Comment entity) {
        return new CommentResponseDto(entity.getId(), entity.getContent(), entity.getCreatedAt(), entity.getReplies().size());
    }
}
