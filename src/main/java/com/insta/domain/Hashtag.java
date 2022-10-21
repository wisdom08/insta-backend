package com.insta.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    protected Hashtag() {}

    protected Hashtag(String name) {
        this.name = name;
    }

    public static Hashtag createHashTag(String name) {
        return new Hashtag(name);
    }
}
