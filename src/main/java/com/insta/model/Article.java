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
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    protected Article() {}

    private Article(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Article createArticle(String title, String content, User user) {
        return new Article(title, content, user);
    }

    public void updateArticle(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
