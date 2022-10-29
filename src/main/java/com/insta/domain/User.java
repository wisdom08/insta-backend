package com.insta.domain;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class User extends Timestamp {

    @PrePersist
    public void prePersist() {
        this.bio = this.bio == null ? "" : this.bio;
    }

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String bio;

    @OneToMany(mappedBy = "user")
    private final List<Heart> haerts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<Article> articles = new ArrayList<>();

    protected User() {}

    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static User createUser(String username, String password) {
        return new User(username, password);
    }

    public void updateUser(String bio) {
        this.bio = bio;
    }
}
