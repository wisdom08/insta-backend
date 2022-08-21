package com.insta.dto.article;

import com.insta.model.Article;
import lombok.Getter;

@Getter
public class ArticleResponseDto {
    private Long id;
    private int numberOfComments;

    protected ArticleResponseDto() {}

    private ArticleResponseDto(Long id, int numberOfComments) {
        this.id = id;
        this.numberOfComments = numberOfComments;
    }

    public static ArticleResponseDto from(Article entity) {
      return new ArticleResponseDto(entity.getId(), entity.getComments().size());
    }
}
