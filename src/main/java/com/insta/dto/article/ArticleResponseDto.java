package com.insta.dto.article;

import com.insta.model.Article;
import com.insta.model.Image;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleResponseDto {
    private Long id;
    private int numberOfComments;
    private int numberOfLikes;
    private Object[] images;

    protected ArticleResponseDto() {}

    private ArticleResponseDto(Long id, int numberOfComments, int numberOfLikes, List<Image> images) {
        this.id = id;
        this.numberOfComments = numberOfComments;
        this.numberOfLikes = numberOfLikes;
        this.images = images.stream()
                .map(Image::getImageUrl)
                .toArray();
    }

    public static ArticleResponseDto from(Article entity, List<Image> images) {
      return new ArticleResponseDto(entity.getId(), entity.getComments().size(), entity.getHearts().size(), images);
    }
}
