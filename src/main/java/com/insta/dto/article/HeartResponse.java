package com.insta.dto.article;

import com.insta.domain.Heart;
import lombok.Getter;

@Getter
public class HeartResponse {
    private Long id;
    private String content;
    private int numberOfLikes;

    protected HeartResponse() {}

    private HeartResponse(Long id, String content, int numberOfLikes) {
        this.id = id;
        this.content = content;
        this.numberOfLikes = numberOfLikes;
    }

    public static HeartResponse from(Heart entity) {
      return new HeartResponse(entity.getId(),
              entity.getArticle().getContent(),
              entity.getArticle().getHearts().size());
    }
}
