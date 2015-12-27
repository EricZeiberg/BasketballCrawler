package com.ericzeiberg.basketballcrawler.utils;

import com.ericzeiberg.basketballcrawler.models.Game;
import com.ericzeiberg.basketballcrawler.models.Team;

import java.util.ArrayList;
import java.util.List;

public class MiscUtils {

    public static Team searchTeams(Team t, List<Team> teams){
        for (Team e : teams){
            if (t.getName().equalsIgnoreCase(cleanString(e.getName()))){
                return e;
            }
        }
        return null;
    }

    public static List<Team> replaceTeam(Team t, List<Team> teams){
        for (Team e : teams){
            if (t.getName().equalsIgnoreCase(cleanString(e.getName()))){
                teams.set(teams.indexOf(e), t);
                return teams;
            }
        }
        try {
            throw new Exception("Can't replace team!");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public static Game searchGames(Game g, List<Game> games){
        for (Game a : games){
            if (g.isSameGame(a)){
                return a;
            }
        }
        return null;
    }


    public static String cleanString(String string){
            return string.replaceAll("\u00a0","").replaceAll("\\(.*\\)", "").replaceAll(" ", "");
    }


    public static String[] cleanString(String[] strings){
        List<String> cleanedStrings = new ArrayList<>();
        for (String s : strings){
            cleanedStrings.add(cleanString(s));
        }
        return cleanedStrings.toArray(new String[cleanedStrings.size()]);
    }
}
