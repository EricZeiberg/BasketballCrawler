package com.ericzeiberg.basketballcrawler.utils;

import com.ericzeiberg.basketballcrawler.models.Game;
import com.ericzeiberg.basketballcrawler.models.Team;

import java.util.ArrayList;
import java.util.List;

public class MiscUtils {


    public static Game searchGames(Game g, List<Game> games){  // Makes sure that no duplicate games are entered into the db
        for (Game a : games){
            if (g.isSameGame(a)){
                return a;
            }
        }
        return null;
    }


    public static String cleanString(String string){
            return string.replaceAll("\u00a0","").replaceAll("\\(.*\\)", "").replaceAll(" ", "");  // This removes extra spaces and weird characters from the strings
    }


    public static String[] cleanString(String[] strings){
        List<String> cleanedStrings = new ArrayList<>();
        for (String s : strings){
            cleanedStrings.add(cleanString(s));
        }
        return cleanedStrings.toArray(new String[cleanedStrings.size()]);
    }
}
