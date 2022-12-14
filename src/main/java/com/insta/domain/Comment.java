package com.insta.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Comment extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Comment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private final List<Heart> hearts = new ArrayList<>();

    protected Comment() {}

    private Comment(String content, Article article, User user) {
        this.content = content;
        this.article = article;
        this.user = user;
    }

    public Comment(String content, Article article, Comment parent, User user) {
        this.content = content;
        this.article = article;
        this.parent = parent;
        this.user = user;
    }

    public static Comment createComment(Article article, String content, User user) {
        return new Comment(content, article, user);
    }

    public static Comment createReply(Article article, Comment parentComment, String content, User user) {
        return new Comment(content, article, parentComment, user);
    }

    public void addHearts(Heart heart) {
        this.hearts.add(heart);
        heart.belongTo(this);
    }
}
