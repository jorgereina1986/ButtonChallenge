package com.jorgereina.www.buttonchallenge.models;

public class User {

    private int id;
    private String name;
    private String email;
    private String candidate;

    public User(int id, String name, String email, String candidate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.candidate = candidate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }
}
