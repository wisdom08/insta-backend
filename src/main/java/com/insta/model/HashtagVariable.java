package com.insta.model;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class HashtagVariable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private Article article;

    protected HashtagVariable() {}

    private HashtagVariable(Hashtag hashtag, Article article) {
        this.hashtag = hashtag;
        this.article = article;
    }

    public static HashtagVariable createHashtagVariable(Hashtag hashtag, Article article) {
        return new HashtagVariable(hashtag, article);
    }
}
