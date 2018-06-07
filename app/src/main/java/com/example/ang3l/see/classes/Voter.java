package com.example.ang3l.see.classes;

public class Voter {
    private String email, name, location, gender, birthday, password, profile;

    private static Voter self;

    public static synchronized Voter getInstance(String email, String name) {
        if (self == null)
            self = new Voter(email, name);
        return self;
    }

    public static void init(String email, String name) {
        self = new Voter(email, name);
    }

    private Voter(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return self.name;
    }

    public String getEmail() {
        return self.email;
    }


}
