package com.insta.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Article extends Timestamped{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private final List<Heart> hearts = new ArrayList<>();

    protected Article() {}

    private Article(String content, User user){
        this.content = content;
        this.user = user;
    }

    public static Article createArticle(String content, User user) {
        return new Article(content, user);
    }

    public void updateArticle(String content) {
        this.content = content;
    }

    public void addHearts(Heart heart) {
        this.hearts.add(heart);
        heart.belongTo(this);
    }
}
