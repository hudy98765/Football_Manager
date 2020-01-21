package com.company;

public class Stadium<T extends League> {
    private String name;
    private int numPeople;
    private int price;

    public Stadium(String name,int numPeople) {
        this.name = name;
        this.numPeople = numPeople;
        if(numPeople > 0 && numPeople <=10000){
            this.price= numPeople*2;
        }else if(numPeople>10000 && numPeople <=40000){
            this.price = numPeople*3;
        }else{
            this.price = numPeople*4;
        }
    }

    public String getName() {
        return name;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return this.name + " stadium: " + "\n"
                + this.numPeople + " people" +"\n"
                + this.price + " USD";
    }
}
