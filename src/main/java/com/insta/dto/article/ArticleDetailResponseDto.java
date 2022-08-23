package com.insta.dto.article;

import com.insta.model.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleDetailResponseDto {
    private Long id;
    private String content;
    private int numberOfLikes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    protected ArticleDetailResponseDto() {}

    public ArticleDetailResponseDto(Long id, String content, int numberOfLikes, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.content = content;
        this.numberOfLikes = numberOfLikes;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ArticleDetailResponseDto from(Article entity) {
        return new ArticleDetailResponseDto(entity.getId(), entity.getContent(), entity.getHearts().size(), entity.getCreatedAt(), entity.getModifiedAt());
    }
}
