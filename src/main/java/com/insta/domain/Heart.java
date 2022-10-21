package com.insta.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    protected Heart() {}

    private Heart(User user, Article article) {
        this.user = user;
        this.article = article;
    }

    private Heart(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    public static Heart Like(User user, Article article) {
        return new Heart(user, article);
    }

    public static Heart Like(User user, Comment comment) {
        return new Heart(user, comment);
    }

    public void belongTo(Article article) {
        this.article = article;
    }

    public void belongTo(Comment comment) {
        this.comment = comment;
    }
}

