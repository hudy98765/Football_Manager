package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Team<T extends Player> implements Comparable<Team<T>> {
    private String name;
    int lost = 0;
    int won = 0;
    int played =0;
    int tied =0;
    private int money=0;
    MyTimerTask myTimerTask = new MyTimerTask();

    ArrayList<T> members = new ArrayList<>();
    ArrayList<Stadium> stadiums = new ArrayList<>();

    public Team(String name, int money) {
        this.name = name;
        if(money>0){
            this.money = money;
        }
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<Stadium> getStadiums() {
        return stadiums;
    }

    public ArrayList<T> getMembers() {
        return members;
    }

    public boolean buyFreePlayer(T player){
        if(members.contains(player)){
            System.out.println("Player " + player.getName() + " is already in the team");
            return false;
        }else if(getMoney() < player.getPrice()) {
            System.out.println("You don't have enough money to buy " + player.getName());
            System.out.println("He costs: " + player.getPrice() + " USD");
            System.out.println("Money left: " + getMoney() + " USD");
            return false;
        }else{
            members.add(player);
            this.money -= player.getPrice();
            System.out.println( player.getName() + " bought to the team " + this.name + " for " + player.getPrice() + " USD");
            return true;
        }

    }
    public int numPlayer(){
        return this.members.size();
    }

    public void matchPlay(Team<T> oponnent, int ourScore, int theirScore){

        String massage;
        if(ourScore > theirScore){
            massage = " won to " + "(" + ourScore + ":" + theirScore + ") ";
            won++;
        }else if(ourScore==theirScore){
            massage = " draw to " + "(" + ourScore + ":" + theirScore + ") ";
            tied++;

        }else{
            massage = " lost to "+ "(" + ourScore + ":" + theirScore + ") ";
            lost++;
        }
        played++;
        if(oponnent != null){
            myTimerTask.playGame();
            System.out.println(this.getName() + massage + oponnent.getName());
            oponnent.matchPlay(null, theirScore, ourScore);
        }
    }

    public boolean rentStadium(Stadium stadium){
        if(stadiums.contains(stadium)) {
            System.out.println("You've already rent a " + stadium.getName() + " stadium!");
            System.out.println("It costs: " + stadium.getPrice());
            return false;
        }
        if(stadium != null && money >= stadium.getPrice()){
            stadiums.add(stadium);
            System.out.println("Congrats! You rent a new " + stadium.getName() + " stadium" );
            this.money -=stadium.getPrice();
            return true;
        }
        return false;
    }

    public boolean buyPlayer(Team<T> team, T player){
        if((!team.getMembers().contains(player)) || (player==null)){
            System.out.println("Such a player doesn't exists in " + team.getName() + " team");
            return false;
        }else if(this.getMoney() >= player.getPrice()){
            this.buyFreePlayer(player);
            team.getMembers().remove(player);
            team.money += player.getPrice();
            return true;
        }else{
            System.out.println("You don't have enough money");
            return false;
        }

    }


    public int ranking(){
        return (won *3 ) + tied;
    }

    @Override
    public int compareTo(Team<T> team) {
        if(this.ranking() > team.ranking()){
            return -1;
        }else if(this.ranking() < team.ranking()){
            return 1;
        }else{
            return 0;
        }

    }

     public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("Match has started at: " + new Date());
            completeTask();
            System.out.println("Match has finished at: " + new Date());
        }

        private void completeTask() {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void playGame(){
            TimerTask timerTask = new MyTimerTask();
            //running timer task as daemon thread
            Timer timer = new Timer(true);
            // timer.scheduleAtFixedRate(timerTask, 0, 2000);
            timer.schedule(timerTask,0);
            //System.out.println("TimerTask started");
            //cancel after sometime
            try {
                Thread.sleep(21000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer.cancel();
            //System.out.println("TimerTask cancelled");
         }
    }
}
