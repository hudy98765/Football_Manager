package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    public static final String DATA = "players.db";
    public static final String CONNECTION = "jdbc:sqlite:C:\\Users\\Kamil\\Desktop\\Data Science\\Java\\Football_Manager\\" + DATA;
    public static final String TABLE_PLAYERS = "players";
    public static final String PLAYERS_COLUMN_ID = "id";
    public static final String PLAYERS_COLUMN_NAME= "name";
    public static final String PLAYERS_COLUMN_PRICE= "price";
    public static final String PLAYERS_COLUMN_TEAMID= "teamID";
    public static final String QUERY_PLAYERS = "SELECT " + TABLE_PLAYERS + "." + PLAYERS_COLUMN_NAME + ", " +
            TABLE_PLAYERS + "." + PLAYERS_COLUMN_PRICE + ", " +  TABLE_PLAYERS + "." + PLAYERS_COLUMN_ID + ", " +
            TABLE_PLAYERS + "." + PLAYERS_COLUMN_TEAMID +
            " FROM " + TABLE_PLAYERS;



    //public static final String DATA_TEAMS = "teams.db";
    //public static final String CONNECTION2 = "jdbc:sqlite:C:\\Users\\Kamil\\Desktop\\Data Science\\Java\\Football_Manager\\" + DATA_TEAMS;
    public static final String TABLE_TEAMS = "teams";
    public static final String TEAMS_COLUMN_NAME = "name";
    public static final String TEAM_COLUMN_LOST = "lost";
    public static final String TEAM_COLUMN_WON = "won";
    public static final String TEAM_COLUMN_TIED = "tied";
    public static final String TEAM_COLUMN_PLAYED = "played";
    public static final String TEAM_COLUMN_MONEY = "money";
    public static final String TEAM_COLUMN_ID = "id";
    public static final String TEAM_COLUMN_RANK = "points";

    public static final String QUERY_TEAMS = "SELECT * FROM " + TABLE_TEAMS;
    public static final String UPDATE_TEAMS_MONEY = "UPDATE " + TABLE_TEAMS + " SET " +
            TEAM_COLUMN_MONEY + "=? WHERE " + TEAM_COLUMN_ID + "=?";
    public static final String UPDATE_TEAMS_RANK = "UPDATE " + TABLE_TEAMS + " SET " +
            TEAM_COLUMN_RANK + "=?" + "," + TEAM_COLUMN_LOST + "=?," + TEAM_COLUMN_WON + "=?," +
            TEAM_COLUMN_TIED + "=?," + TEAM_COLUMN_PLAYED + "=?" + " WHERE " + TEAM_COLUMN_ID + "=?";
    public static final String UPDATE_PLAYER_TEAMID = "UPDATE " + TABLE_PLAYERS + " SET " +
            PLAYERS_COLUMN_TEAMID + " = ?" + " WHERE " + PLAYERS_COLUMN_ID + "=?";

    
    private Connection conn;
    private PreparedStatement updateTeamMoney;
    private PreparedStatement updateTeamsPoints;
    private PreparedStatement updatePlayerTeamID;
    public boolean open(){
        try{
            conn = DriverManager.getConnection(CONNECTION);
            updateTeamMoney = conn.prepareStatement(UPDATE_TEAMS_MONEY);
            updateTeamsPoints = conn.prepareStatement(UPDATE_TEAMS_RANK);
            updatePlayerTeamID = conn.prepareStatement(UPDATE_PLAYER_TEAMID);
            return true;
        }catch(SQLException e){
            System.out.println("Couldn't load the database " + e.getMessage());
            return false;
        }
    }


    public void close(){
        try{
            if(conn !=null){
                conn.close();
            }
            if(updateTeamMoney !=null){
                updateTeamMoney.close();
            }
            if(updateTeamsPoints !=null){
                updateTeamsPoints.close();
            }
            if(updatePlayerTeamID != null){
                updatePlayerTeamID.close();
            }

        }catch (SQLException e){
            System.out.println("Couldn't close database " + e.getMessage());
        }
    }

    public List<FootballPlayer> queryForPlayers(){
        StringBuilder sb = new StringBuilder(QUERY_PLAYERS);
        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sb.toString())){
            List<FootballPlayer> players = new ArrayList<>();
            while(results.next()){
                FootballPlayer player = new FootballPlayer();
                player.setName(results.getString(1));
                player.setPrice(results.getInt(2));
                player.setId(results.getInt(3));
                player.setTeamID(results.getInt(4));
                players.add(player);
            }
            return players;

        }catch(SQLException e){
            System.out.println("Query failed " + e.getMessage());
            return null;
        }
    }

    public List<Team<FootballPlayer>> queryForTeams(){
        StringBuilder sb = new StringBuilder(QUERY_TEAMS);
        try(Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery(sb.toString())){

            List<Team<FootballPlayer>> teams = new ArrayList<>();
            while(results.next()){
                Team<FootballPlayer> team = new Team<FootballPlayer>();
                team.setId(results.getInt(1));
                team.setName(results.getString(2));
                team.setLost(results.getInt(3));
                team.setWon(results.getInt(4));
                team.setTied(results.getInt(5));
                team.setPlayed(results.getInt(6));
                team.setMoney(results.getInt(7));
                team.setRankPoints(results.getInt(8));
                teams.add(team);
            }
            return teams;
        }catch(SQLException e){
            System.out.println("Query failed " + e.getMessage());
            return null;
        }
    }

    public boolean updateTeamsMoney(int money, int id){
        //System.out.println(UPDATE_TEAMS_MONEY);
        try{
            updateTeamMoney.setInt(1,money);
            updateTeamMoney.setInt(2,id);
            int affectedRows = updateTeamMoney.executeUpdate();
            return affectedRows==1;
        }catch(SQLException e){
            System.out.println("Update failed " + e.getMessage());
            return false;
        }
    }

    public boolean updateTeamsPoints(int points, int id, int won, int lost, int tied, int played){
        try{
            updateTeamsPoints.setInt(1,points);
            updateTeamsPoints.setInt(2, lost);
            updateTeamsPoints.setInt(3, won);
            updateTeamsPoints.setInt(4, tied);
            updateTeamsPoints.setInt(5, played);
            updateTeamsPoints.setInt(6,id);
            int affectedRows = updateTeamsPoints.executeUpdate();
            return affectedRows==1;
        }catch(SQLException e){
            System.out.println("Update failed " + e.getMessage());
            return false;
        }
    }
    public boolean updatePlayerTeamId(int teamID, int id){
        try{
            updatePlayerTeamID.setInt(1,teamID);
            updatePlayerTeamID.setInt(2,id);
            int affectedRows = updatePlayerTeamID.executeUpdate();
            return affectedRows==1;
        }catch(SQLException e){
            System.out.println("Update failed " + e.getMessage());
            return false;
        }
    }


}
