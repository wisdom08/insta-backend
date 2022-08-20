package com.insta.dto.article;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ArticleRequestDto {
    private final String title;
    private final String contents;
    private final String cntComment;
}
