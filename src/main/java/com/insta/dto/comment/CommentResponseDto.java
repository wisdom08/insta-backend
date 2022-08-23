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
    private int numberOfLikes;


    protected CommentResponseDto() {}

    private CommentResponseDto(Long id, String content, int numberOfLikes, LocalDateTime createdAt, int numberOfReply) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.numberOfReply = numberOfReply;
        this.numberOfLikes = numberOfLikes;
    }

    public static CommentResponseDto from(Comment entity) {
        return new CommentResponseDto(entity.getId(), entity.getContent(), entity.getHearts().size(), entity.getCreatedAt(), entity.getReplies().size());
    }
}
