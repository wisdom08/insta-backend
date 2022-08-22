package com.insta.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User extends Timestamped {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String userintro;

    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static User createUser(String username, String password) {
        return new User(username, password);
    }
}
