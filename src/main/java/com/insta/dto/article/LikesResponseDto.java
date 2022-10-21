package com.insta.dto.article;

import com.insta.domain.Heart;
import lombok.Getter;

@Getter
public class LikesResponseDto {
    private Long id;
    private String content;
    private int numberOfLikes;

    protected LikesResponseDto() {}

    private LikesResponseDto(Long id, String content, int numberOfLikes) {
        this.id = id;
        this.content = content;
        this.numberOfLikes = numberOfLikes;
    }

    public static LikesResponseDto from(Heart entity) {
      return new LikesResponseDto(entity.getId(),
              entity.getArticle().getContent(),
              entity.getArticle().getHearts().size());
    }
}
