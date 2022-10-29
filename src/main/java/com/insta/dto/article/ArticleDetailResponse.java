package com.insta.dto.article;

import com.insta.domain.Article;
import com.insta.domain.Image;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ArticleDetailResponse {
    private Long id;
    private String content;
    private int numberOfLikes;
    private final List<String> hashtags = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Object[] images;

    protected ArticleDetailResponse() {}

    public ArticleDetailResponse(Long id, String content, int numberOfLikes, LocalDateTime createdAt, LocalDateTime modifiedAt, List<Image> images) {
        this.id = id;
        this.content = content;
        this.numberOfLikes = numberOfLikes;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.images = images.stream()
                .map(Image::getImageUrl)
                .toArray();
    }

    public static ArticleDetailResponse from(Article entity, List<Image> images) {
        return new ArticleDetailResponse(entity.getId(), entity.getContent(), entity.getHearts().size(), entity.getCreatedAt(), entity.getModifiedAt(), images);
    }

    public void addHashtag(String hashtags) {
        this.hashtags.add(hashtags);
    }
}
