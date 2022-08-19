package com.insta.service;

import com.insta.dto.article.ArticleRequestDto;
import com.insta.model.Articles;
import com.insta.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;


    public String createArticles(ArticleRequestDto requestDto){
        Articles articles = new Articles(requestDto);
        articleRepository.save(articles);
        return "게시글 작성이 성공되었습니다";
    }

}
