package com.ericzeiberg.basketballcrawler;

import com.ericzeiberg.basketballcrawler.models.Game;
import com.ericzeiberg.basketballcrawler.models.Team;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;

public class BasketballCrawler {


    public static void main(String[] args){
        final Morphia morphia = new Morphia();

        morphia.mapPackage("com.ericzeiberg.basketballcrawler");

        final Datastore datastore = morphia.createDatastore(new MongoClient("localhost" , 27017 ), "BasketballCrawler1");
        datastore.ensureIndexes();

        Crawler.parseDivisionPage(Division.LL);
        Crawler.parseDivisionPage(Division.L);
        Crawler.parseDivisionPage(Division.M);
        Crawler.parseDivisionPage(Division.S);

        List<Team> teams = Crawler.teams;
        List<Game> games = Crawler.games;


        List<Team> teamsToRemove = new ArrayList<>();
        for (Team t : teams){
            if (t.getWins() == 0 && t.getLosses() == 0 && t.getTies() == 0){
                teamsToRemove.add(t);
            }
        }

        teams.removeAll(teamsToRemove);
        teamsToRemove.clear();

        for (int i = 0; i < teams.size(); i++){
            for (int i1 = i+1; i1 < teams.size(); i1++){
                if (teams.get(i).equals(teams.get(i1))){
                    teams.get(i).setWins(teams.get(i).getWins() + teams.get(i1).getWins());
                    teams.get(i).setLosses(teams.get(i).getLosses() + teams.get(i1).getLosses());
                    teamsToRemove.add(teams.get(i1));
                }
            }
        }

        teams.removeAll(teamsToRemove);

        datastore.save(teams);
        datastore.save(games);




    }




}
