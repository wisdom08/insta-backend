package com.insta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insta.dto.article.ArticleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "articles")
//@Table(name = "articles")
public class Articles extends Timestamped{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

//    @Column(nullable = false)
//    private Long cntComment;



//    @OneToMany(mappedBy = "articles", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "articles", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Image> image = new ArrayList<>();
//
//    @JsonIgnore//@JsonIgnore 어노테이션은 클래스의 속성(필드, 멤버변수) 수준에서 직렬화 역직렬화에 사용되는 논리적 프로퍼티(속성..) 값을 무시할때 사용
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "user_id")//조인 컬럼은 외래 키를 매핑할 때 사용
//    private User user;

    public void update(ArticleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
//    public void confirmArticles(User user) {
//        this.user = user;
//        user.addArticlesList(this);
//    }

    public Articles(ArticleRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
//        this.cntComment = 0L;
    }
}
