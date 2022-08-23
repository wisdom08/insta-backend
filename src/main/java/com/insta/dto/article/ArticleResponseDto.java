package com.insta.dto.article;

import com.insta.model.Article;
import lombok.Getter;

@Getter
public class ArticleResponseDto {
    private Long id;
    private int numberOfComments;
    private int numberOfLikes;

    protected ArticleResponseDto() {}

    private ArticleResponseDto(Long id, int numberOfComments, int numberOfLikes) {
        this.id = id;
        this.numberOfComments = numberOfComments;
        this.numberOfLikes = numberOfLikes;
    }

    public static ArticleResponseDto from(Article entity) {
      return new ArticleResponseDto(entity.getId(), entity.getComments().size(), entity.getHearts().size());
    }
}
