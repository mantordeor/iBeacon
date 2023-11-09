package com.example.weeeapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String name;

    public User(int signInId, String name) {
        this.id = signInId;
        this.name = name;
    }
}