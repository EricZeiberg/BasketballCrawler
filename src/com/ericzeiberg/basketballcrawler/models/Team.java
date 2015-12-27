package com.ericzeiberg.basketballcrawler.models;


import com.ericzeiberg.basketballcrawler.utils.MiscUtils;
import com.sun.tools.hat.internal.util.Misc;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.List;

@Entity("teams")
public class Team {

    @Id
    private ObjectId id;

    private String name;
    int wins = 0;
    int losses = 0;
    int ties = 0;



    public Team(ObjectId id, String name, int wins, int losses, int ties) {
        this.id = id;
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public Team(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }

    public ObjectId getId() {
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

    public void setTies(int ties) {
        this.ties = ties;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", ties=" + ties +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if (o != null && o instanceof Team){
            Team t = (Team) o;
            return MiscUtils.cleanString(t.getName()).equalsIgnoreCase(MiscUtils.cleanString(name));  // If two teams share the same name, they are equal to eachother. 
        }
        else {
            return false;
        }
    }
}
