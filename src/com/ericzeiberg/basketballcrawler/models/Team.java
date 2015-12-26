package com.ericzeiberg.basketballcrawler.models;


import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.List;

@Entity("teams")
public class Team {

    @Id
    private long id;

    private String name;
    int wins;
    int losses;
    int ties;

    @Reference
    private List<Game> games;

    public Team(String name, int wins, int losses, int ties, List<Game> games) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.games = games;
    }

    public Team(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public List<Game> getGames() {
        return games;
    }

    public void addGame(Game g){
        if (games.contains(g)){
            return;
        }
        games.add(g);
    }

    public void removeGame(Game g){
        if (games.contains(g)){
            games.remove(g);
        }
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
