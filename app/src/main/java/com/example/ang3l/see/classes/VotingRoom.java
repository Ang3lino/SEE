package com.example.ang3l.see.classes;

public class VotingRoom {
    private int number; // number of our voting room
    private boolean activated; // can we vote ?
    private String creator; // email of creator

    private static VotingRoom self;

    public static synchronized VotingRoom init(int number, boolean activated, String creator) {
        if (self == null)
            self = new VotingRoom(number, activated, creator);
        return self;
    }

    /**
     * retorna una unica instancia, es importante haberla creado previamente
     * @return self
     */
    public static synchronized VotingRoom get() {
        return self;
    }

    private VotingRoom(int number, boolean activated, String creator) {
        this.number = number;
        this.activated = activated;
        this.creator = creator;
    }

    public void setNumber(int number) {
        self.number = number;
    }

    public void setActivated(boolean activated) {
        self.activated = activated;
    }

    public void setCreator(String creator) {
        self.creator = creator;
    }

    public int getNumber() {
        return self.number;
    }

    public String getCreator() {
        return self.creator;
    }

    public boolean isActivated() {
        return self.activated;
    }

}
