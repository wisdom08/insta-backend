package com.insta.dto.article;

import com.insta.model.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleDetailResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    protected ArticleDetailResponseDto() {}

    public ArticleDetailResponseDto(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ArticleDetailResponseDto from(Article entity) {
        return new ArticleDetailResponseDto(entity.getId(), entity.getTitle(), entity.getContent(), entity.getCreatedAt(), entity.getModifiedAt());
    }
}
