package com.ericzeiberg.basketballcrawler.models;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;


@Entity("Games")
public class Game {

    @Id
    private long id;

    @Reference
    private Team homeTeam;

    @Reference
    private Team awayTeam;

    private String location;
    private String date;
    private int winPts;
    private int lossPts;
    private boolean homeTeamWon;

    public Game(Team homeTeam, Team awayTeam, String location, String date, int winPts, int lossPts, boolean homeTeamWon) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.location = location;
        this.date = date;
        this.winPts = winPts;
        this.lossPts = lossPts;
        this.homeTeamWon = homeTeamWon;
    }

    public String getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public String getLocation() {
        return location;
    }

    public int getWinPts() {
        return winPts;
    }

    public int getLossPts() {
        return lossPts;
    }

    public boolean isHomeTeamWon() {
        return homeTeamWon;
    }


}
