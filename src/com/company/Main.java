package com.company;

import java.lang.reflect.Array;
import java.util.*;

public class Main {
private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        FootballPlayer John = new FootballPlayer("John", 2000);
        FootballPlayer CR = new FootballPlayer("Cristiano", 122000);
        FootballPlayer LW = new FootballPlayer("Robert", 20320);
        FootballPlayer JG = new FootballPlayer("Jake", 130);
        FootballPlayer ZL = new FootballPlayer("Zlatan", 20100);

        Team<FootballPlayer> ManU = new Team<>("Man United", 100000);
        Team<FootballPlayer> Albatros = new Team<>("Albatros",200000);
        Team<BaseballPlayer> Mustangs = new Team<>("Mustangs",110000);

        System.out.println("----------------------------------");
        //adding players
        ManU.buyFreePlayer(John);
        ManU.buyFreePlayer(CR);
        ManU.buyFreePlayer(LW);
        System.out.println("----------------------------------");

        Albatros.buyFreePlayer(JG);
        Albatros.buyFreePlayer(ZL);
        System.out.println("----------------------------------");

        showPlayers(ManU);
        showPlayers(Albatros);
        System.out.println("----------------------------------");

        League<Team<FootballPlayer>> league1 = new League<>("League 1");
        league1.addTeam(ManU);
        league1.addTeam(Albatros);

        Stadium<League<Team<FootballPlayer>>> stadium1 = new Stadium<>("Alianz Arena", 1000);
        Stadium<League<Team<FootballPlayer>>> stadium2 = new Stadium<>("San Siro", 20000);

        System.out.println("----------------------------------");
        ManU.rentStadium(stadium1);
        ManU.rentStadium(stadium2);
        System.out.println("----------------------------------");

        checkMoney(ManU);
        System.out.println("----------------------------------");
        ManU.buyPlayer(Albatros,JG);
        showPlayers(ManU);
        showPlayers(Albatros);
        checkMoney(ManU);
        System.out.println("----------------------------------");

        //System.out.println(ManU.getStadiums());;
        System.out.println("Do you want to play a match? (Yes/No)");
        String decision = scanner.nextLine().toUpperCase();
        int max = 5;
        int min = 0;
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        int rand1 = (int)(Math.random() * range) + min+1;
        switch (decision){
            case "YES":
                ManU.matchPlay(Albatros, rand,rand1);
                break;
            case "NO":
                break;
            default:
                break;
        }

        league1.showTable();

    }

    public static void showPlayers(Team<FootballPlayer> team){
        System.out.println("Players in " + team.getName());
        int i =1;
        for (FootballPlayer members : team.getMembers()) {
            System.out.println(i + ". " + members.getName());
            i++;
        }
    }

    public static void checkMoney(Team<FootballPlayer> team){
        System.out.println("----------------------------------");
        System.out.println("Current" + team.getName() + " balance: " + team.getMoney());
        System.out.println("----------------------------------");
    }

}
