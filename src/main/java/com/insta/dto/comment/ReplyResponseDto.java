package com.insta.dto.comment;

import com.insta.model.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    protected ReplyResponseDto() {}

    private ReplyResponseDto(Long id, String content, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static ReplyResponseDto from(Comment entity) {
        return new ReplyResponseDto(entity.getId(), entity.getContent(), entity.getCreatedAt());
    }
}
