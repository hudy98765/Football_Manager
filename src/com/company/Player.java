package com.company;

public abstract class Player {
    private String name;
    private int price;
    private int id;
    private int teamID=0;

    public Player(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Player(){
        this.name =null;
        this.price =0;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
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

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
