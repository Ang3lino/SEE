package com.example.ang3l.see.classes;

public class VotingRoom {
    private int number; // number of our voting room
    private boolean activated; // can we vote ?
    private String creator; // email of creator

    public VotingRoom(int number, boolean activated, String creator) {
        this.number = number;
        this.activated = activated;
        this.creator = creator;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getNumber() { return number; }

    public String getCreator() { return creator; }

    public boolean isActivated() { return activated; }

}
