package com.ericzeiberg.basketballcrawler;

import com.ericzeiberg.basketballcrawler.models.Game;
import com.ericzeiberg.basketballcrawler.models.Team;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.List;

public class BasketballCrawler {


    static final String DB_NAME = "BasketballCrawler";

    public static void main(String[] args){
        final Morphia morphia = new Morphia();

        morphia.mapPackage("com.ericzeiberg.basketballcrawler");

        final Datastore datastore = morphia.createDatastore(new MongoClient("localhost" , 27017 ), DB_NAME); // Connect to local database and store data in DB_NAME collection
        datastore.ensureIndexes();

        Crawler.parseDivisionPage(Division.LL);  // Parse all divisions of CIAC
        Crawler.parseDivisionPage(Division.L);
        Crawler.parseDivisionPage(Division.M);
        Crawler.parseDivisionPage(Division.S);

        List<Team> teams = Crawler.teams;  // Grab static arrays from Crawler class
        List<Game> games = Crawler.games;

        datastore.save(teams);  // Store arrays in database
        datastore.save(games);


    }




}
