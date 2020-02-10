package com.company;

import java.util.*;

public class Main {
private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        if(!datasource.open()){
            System.out.println("Couldn't open datasource");
            return;
        }
        List<FootballPlayer> players = datasource.queryForPlayers();
        for(FootballPlayer player: players){
            System.out.println(player.getId() + ". " + player.getName() + ", price: " + player.getPrice() + ", " + player.getTeamID());
        }

        List<Team<FootballPlayer>> teams = datasource.queryForTeams();

         Team<FootballPlayer> MU = teams.get(0);
         Team<FootballPlayer> BM = teams.get(1);
         Team<FootballPlayer> ACM = teams.get(2);
         Team<FootballPlayer> RM = teams.get(3);

        for(Team<FootballPlayer> team: teams){
            for(FootballPlayer player: players){
                if(player.getTeamID() !=0 && player.getTeamID() == team.getId()){
                    team.buyFreePlayer(player);
                }
            }
        }

        System.out.println("----------------------------------");
        //adding players

        ACM.buyFreePlayer(players.get(0));
        ACM.buyFreePlayer(players.get(1));
        ACM.buyFreePlayer(players.get(2));

        System.out.println("----------------------------------");
        MU.buyFreePlayer(players.get(3));
        MU.buyFreePlayer(players.get(4));

        System.out.println("----------------------------------");
        showPlayers(ACM);
        showPlayers(MU);
        System.out.println("----------------------------------");

        League<Team<FootballPlayer>> league1 = new League<>("League 1");
        league1.addTeam(ACM);
        league1.addTeam(MU);
        league1.addTeam(BM);
        league1.addTeam(RM);

        /*Stadium<League<Team<FootballPlayer>>> stadium1 = new Stadium<>("Alianz Arena", 1000);
        Stadium<League<Team<FootballPlayer>>> stadium2 = new Stadium<>("San Siro", 20000);*/

        System.out.println("----------------------------------");
        ACM.buyPlayer(MU,players.get(4));
        showPlayers(ACM);
        showPlayers(MU);
        System.out.println("----------------------------------");


        System.out.println("Available teams: ");
        for(Team<FootballPlayer> team: teams){
            System.out.println(team.getId() + ". " + team.getName());
        }
        System.out.println("Pick the team (number)");
        int teamNumber = scanner.nextInt();
        scanner.nextLine();
        Team<FootballPlayer> yourTeam;
        switch(teamNumber){
            case 1:
                yourTeam = teams.get(0);
                break;
            case 2:
                yourTeam = teams.get(1);
                break;
            case 3:
                yourTeam = teams.get(2);
                break;
            case 4:
                yourTeam = teams.get(3);
                break;
            default:
                yourTeam = teams.get(0);
        }


        System.out.println("Do you want to play a match? (Yes/No)");
        String decision = scanner.nextLine().toUpperCase();
        int max = 5;
        int min = 0;
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        int rand1 = (int)(Math.random() * range) + min+1;
        switch (decision){
            case "YES":
                yourTeam.matchPlay(ACM, rand,rand1);
                break;
            case "NO":
                break;
            default:
                break;
        }

        league1.showTable();
        //update database
        for(Team<FootballPlayer> team: league1.getLeague()){
            datasource.updateTeamsPoints(team.getRankPoints(), team.getId(), team.won,team.lost,team.tied,team.played);
        }

       datasource.close();
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
