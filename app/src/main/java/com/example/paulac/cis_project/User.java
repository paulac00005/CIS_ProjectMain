package com.example.paulac.cis_project;

/**
 * Created by paulac on 2/22/16.
 */
public class User {
    String username, password, key;

    public User(String username, String password, String key){
        this.username = username;
        this.password = password;
        this.key = key;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.key = "70930f27";
    }
}
