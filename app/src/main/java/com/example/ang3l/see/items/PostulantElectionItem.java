package com.example.ang3l.see.items;

public class PostulantElectionItem {
    private String name, match, email;

    public PostulantElectionItem(String name, String match, String email) {
        this.name = name;
        this.match = match;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getMatch() {
        return match;
    }

    public String getEmail() {
        return email;
    }

}
