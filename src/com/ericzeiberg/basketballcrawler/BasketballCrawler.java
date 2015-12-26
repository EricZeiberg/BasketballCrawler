package com.ericzeiberg.basketballcrawler;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class BasketballCrawler {

    public static void main(String[] args){
        final Morphia morphia = new Morphia();
        morphia.mapPackage("com.ericzeiberg.basketballcrawler.models"); // Points Morphia to the models directory


        final Datastore datastore = morphia.createDatastore(new MongoClient(), "BasketballCrawler");
        datastore.ensureIndexes();
    }


}
