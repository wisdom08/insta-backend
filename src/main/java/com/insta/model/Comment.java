package com.insta.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Comment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Comment> replies = new ArrayList<>();

    protected Comment() {}

    private Comment(String content, Article article) {
        this.content = content;
        this.article = article;
    }

    public Comment(String content, Article article, Comment parent) {
        this.content = content;
        this.article = article;
        this.parent = parent;
    }

    public static Comment createComment(Article article, String content) {
        return new Comment(content, article);
    }

    public static Comment createReply(Article article, Comment parentComment, String content) {
        return new Comment(content, article, parentComment);
    }
}
