package com.ericzeiberg.basketballcrawler;

import com.ericzeiberg.basketballcrawler.models.Game;
import com.ericzeiberg.basketballcrawler.models.Team;
import com.ericzeiberg.basketballcrawler.utils.MiscUtils;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crawler {

    static Document doc;
    static List<Team> teams = new ArrayList<>();
    static List<Game> games = new ArrayList<>();

    public static void parseDivisionPage(Division d){
        try {
            doc = Jsoup.connect("http://content.ciacsports.com/scripts/bbb_rankings.cgi?div=" + d.name()).userAgent("Mozilla").get(); // Connect to list of all teams in division page
            Element teamsTable = doc.getElementsByTag("table").get(1);
            Elements teamRows = teamsTable.getElementsByTag("tr");
            int i = 0;
            for (Element e : teamRows){  // Iterate through each row in table
                if (i == 0){
                    i++;
                    continue;
                }
                Element aTag = e.getElementsByTag("td").first().getElementsByTag("font").first().getElementsByTag("a").first();
                Team t = new Team(new ObjectId(), aTag.text());  // Create new team and add it to team array
                System.out.println("Adding new team " + t.getName());
                teams.add(t);
                parseTeamPage(t, aTag.attr("href"));  // Parse individual team schedule for game results
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseTeamPage(Team t, String url){
        int tries = 0;
        while (true){
            try {
                doc = Jsoup.connect(url).userAgent("Mozilla").get();
                break;
            }
            catch (IOException e){
                if (tries > 5){
                    break;
                }
                System.out.println("IOException. Trying again!");
                tries++;

            }
        }

        try {
            Element gamesTable = doc.getElementsByTag("table").first();
            Elements gameRows = gamesTable.getElementsByTag("tr");
            int i = 0;
            for (Element e: gameRows) {  // Iterate through each row of schedule
                if (i < 4){
                    i++;
                    continue;
                }
                if (e.getElementsByTag("td").size() < 4){
                    continue;
                }
                String date = e.getElementsByTag("td").first().text();
                String otherTeam = MiscUtils.cleanString(e.getElementsByTag("td").get(1).text());

                String points = e.getElementsByTag("td").get(3).text();
                if (points.contains("p.m.") || points.contains("a.m.") || points.contains("TBA")){  // If game has not been played yet, skip the row
                    continue;
                }
                String location;
                try {
                     location = e.getElementsByTag("td").get(2).text().split("-")[1];
                }
                catch (ArrayIndexOutOfBoundsException e1){  // Sometimes, location info is missing
                    location = "N/A";
                }
                Game g;
                if (e.getElementsByTag("td").get(2).text().split("-")[0].contains("Home")){
                    if (points.split(" ")[0].contains("W")){  // If team is home for this game and they won, adjust accordingly
                        String[] pointArray = MiscUtils.cleanString(points.split(" ")[1].split("-"));
                        g = new Game(new ObjectId(), t.getName(), otherTeam, location, date, Integer.parseInt(pointArray[0]), Integer.parseInt(pointArray[1]), true);
                        if (MiscUtils.searchGames(g, games) == null){
                            games.add(g);
                        }
                        else{
                            continue;
                        }
                        t.setWins(t.getWins() + 1);
                    }
                    else {  // This part is called if team is home and they lost
                        String[] pointArray = MiscUtils.cleanString(points.split(" ")[1].split("-"));
                        g = new Game(new ObjectId(), t.getName(), otherTeam, location, date, Integer.parseInt(pointArray[0]), Integer.parseInt(pointArray[1]), false);
                        if (MiscUtils.searchGames(g, games) == null){
                            games.add(g);
                        } else{
                            continue;
                        }
                        t.setLosses(t.getLosses() + 1);
                    }
                }
                else {
                    if (points.split(" ")[0].contains("W")){  // If team is away and they won
                        String[] pointArray = MiscUtils.cleanString(points.split(" ")[1].split("-"));
                        g = new Game(new ObjectId(), otherTeam, t.getName(), location, date, Integer.parseInt(pointArray[0]), Integer.parseInt(pointArray[1]), false);
                        if (MiscUtils.searchGames(g, games) == null){
                            games.add(g);
                        } else{
                            continue;
                        }
                        t.setWins(t.getWins() + 1);
                    }
                    else {  // If team is away and they lost
                        String[] pointArray = MiscUtils.cleanString(points.split(" ")[1].split("-"));
                        g = new Game(new ObjectId(), otherTeam, t.getName(), location, date, Integer.parseInt(pointArray[0]), Integer.parseInt(pointArray[1]), true);
                        if (MiscUtils.searchGames(g, games) == null){
                            games.add(g);
                        } else{
                            continue;
                        }
                        t.setLosses(t.getLosses() + 1);
                    }
                }
                i++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }



    }


}
