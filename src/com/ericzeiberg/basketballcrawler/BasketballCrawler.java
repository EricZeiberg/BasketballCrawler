package com.ericzeiberg.basketballcrawler;

import com.ericzeiberg.basketballcrawler.models.Game;
import com.ericzeiberg.basketballcrawler.models.Team;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.List;

public class BasketballCrawler {


    public static void main(String[] args){
        final Morphia morphia = new Morphia();

        morphia.mapPackage("com.ericzeiberg.basketballcrawler");

        final Datastore datastore = morphia.createDatastore(new MongoClient("localhost" , 27017 ), "BBC_RAW");
        datastore.ensureIndexes();

        Crawler.parseDivisionPage(Division.LL);
        Crawler.parseDivisionPage(Division.L);
        Crawler.parseDivisionPage(Division.M);
        Crawler.parseDivisionPage(Division.S);

        List<Team> teams = Crawler.teams;
        List<Game> games = Crawler.games;

        datastore.save(teams);
        datastore.save(games);


    }




}
