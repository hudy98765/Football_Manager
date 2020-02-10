package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class League<T extends Team>  {
    private String leagueName;
    ArrayList<T> league = new ArrayList<>();

    public League(String leagueName) {
        this.leagueName = leagueName;
    }

    public ArrayList<T> getLeague() {
        return league;
    }

    public boolean addTeam(T team){
        if(league.contains(team)){
            System.out.println(team.getName() + " already exist in a league");
            return false;
        }else{
            league.add(team);
            System.out.println(team.getName() + " added succesfully to the league");
            return true;
        }
    }

    public void showTable(){
        System.out.println(leagueName + " ranking table: ");
       Collections.sort(league);
       int i=1;
       for(T t: league){
           System.out.println(i + ". " + t.getName() + ": " + t.getRankPoints() + "p." + " " + " played: " + t.played + " " + "won: " + t.won + " " + "lost: "+ t.lost + " " + "draw :" + t.tied);
           i++;
       }
    }
}
