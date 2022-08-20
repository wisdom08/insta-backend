package com.insta.dto.article;

import com.insta.model.Article;
import lombok.Getter;

@Getter
public class ArticleResponseDto {
    private Long id;

    protected ArticleResponseDto() {}

    private ArticleResponseDto(Long id) {
        this.id = id;
    }

    public static ArticleResponseDto from(Article entity) {
      return new ArticleResponseDto(entity.getId());
    }
}
