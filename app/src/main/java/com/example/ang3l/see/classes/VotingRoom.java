package com.example.ang3l.see.classes;

public class VotingRoom {
    private int number;
    private boolean canWeVote;
    private String creator;

    public VotingRoom(int number, boolean canWeVote, String creator) {
        this.number = number;
        this.canWeVote = canWeVote;
        this.creator = creator;
    }
}
