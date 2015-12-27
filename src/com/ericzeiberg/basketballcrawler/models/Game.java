package com.ericzeiberg.basketballcrawler.models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;


@Entity("Games")
public class Game {

    @Id
    private ObjectId id;

    private String homeTeam;

    private String awayTeam;

    private String location;
    private String date;
    private int winPts;
    private int lossPts;
    private boolean homeTeamWon;

    public Game(ObjectId id, String homeTeam, String awayTeam, String location, String date, int winPts, int lossPts, boolean homeTeamWon) {
        this.id = id;
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

    public ObjectId getId() {
        return id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
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

    public boolean isSameGame(Game g){
        if (g.getLocation().equalsIgnoreCase(location) && g.getDate().equalsIgnoreCase(date)){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", winPts=" + winPts +
                ", lossPts=" + lossPts +
                ", homeTeamWon=" + homeTeamWon +
                '}';
    }
}
