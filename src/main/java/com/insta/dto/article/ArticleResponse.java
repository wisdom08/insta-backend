package com.insta.dto.article;

import com.insta.domain.Article;
import com.insta.domain.Image;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleResponse {
    private Long id;
    private int numberOfComments;
    private int numberOfLikes;
    private Object[] images;

    protected ArticleResponse() {}

    private ArticleResponse(Long id, int numberOfComments, int numberOfLikes, List<Image> images) {
        this.id = id;
        this.numberOfComments = numberOfComments;
        this.numberOfLikes = numberOfLikes;
        this.images = images.stream()
                .map(Image::getImageUrl)
                .toArray();
    }

    public static ArticleResponse from(Article entity, List<Image> images) {
      return new ArticleResponse(entity.getId(), entity.getComments().size(), entity.getHearts().size(), images);
    }
}
