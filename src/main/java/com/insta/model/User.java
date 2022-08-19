package com.insta.model;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Users")
@NoArgsConstructor
public class User {

    @GeneratedValue (
            strategy = GenerationType.IDENTITY
    )

    @Id
    private Long user_id;
    @Column(
            nullable = false
    )

    private String username;
    @Column(
            nullable = false
    )

    private String password;

    @Column
    private String userintro;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.userintro = userintro;
    }

    public void setId(Long user_id) {
        this.user_id = user_id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.username = password;
    }
    public void setUserintro(String userintro) {
        this.userintro = userintro;
    }


    public void getId(Long user_id) {
        this.user_id = user_id;
    }
    public void getUsername(String username) {
        this.username = username;
    }
    public void getPassword(String password) {
        this.username = password;
    }
    public void getUserintro(String userintro) {
        this.userintro = userintro;
    }


}
