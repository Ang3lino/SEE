package com.example.ang3l.see.items;

public class PostulantItem {
    private String name, email;
    private int imageId;

    public PostulantItem(int imageId, String name, String email) {
        this.imageId = imageId;
        this.name = name;
        this.email = email;
    }

    public int getImageId() { return imageId; }

    public String getName() { return name; }

    public String getEmail() { return email; }
}
