package com.company;

public abstract class Player {
    private String name;
    private int price;

    public Player(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
