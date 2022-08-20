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

    public String updateArticles(ArticleRequestDto requestDto, Long id){
        Articles articles = articleRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 게시글이 존재하지 않습니다."));
        String msg;

//        if(){
        articles.update(requestDto);
        articleRepository.save(articles);
        msg = "수정완료";
//        } else{
//            msg = "자신의 게시글만 수정 가능합니다.";
//        }
        return msg;
    }


    public String deleteArticles(Long id){
        Articles articles = articleRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 게시글이 존재하지 않습니다."));
        String msg;
//        if(){
        articleRepository.deleteById(id);
        msg = "삭제 완료";
//        } else{
//            msg = "자신의 게시글만 삭제 가능합니다.";
//        }
        return msg;
    }


}
