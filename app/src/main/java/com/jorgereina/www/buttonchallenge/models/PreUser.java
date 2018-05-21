package com.jorgereina.www.buttonchallenge.models;

/**
 * Created by jorgereina on 3/8/18.
 */

public class PreUser {

    private String name;
    private String email;
    private String candidate;

    public PreUser(String name, String email, String candidate) {
        this.name = name;
        this.email = email;
        this.candidate = candidate;
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
