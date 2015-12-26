package com.ericzeiberg.basketballcrawler;

import com.ericzeiberg.basketballcrawler.models.Game;
import com.ericzeiberg.basketballcrawler.models.Team;
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
            doc = Jsoup.connect("http://content.ciacsports.com/scripts/bbb_rankings.cgi?div=" + d.name()).userAgent("Mozilla").get();
            Element teamsTable = doc.getElementsByTag("table").get(1);
            Elements teamRows = teamsTable.getElementsByTag("tr");
            int i = 0;
            for (Element e : teamRows){
                if (i == 0){
                    i++;
                    continue;
                }
                Element aTag = e.getElementsByTag("td").first().getElementsByTag("font").first().getElementsByTag("a").first();
                Team t = new Team(aTag.text());
                System.out.println("Adding new team " + t.getName());
                teams.add(t);
                parseTeamPage(t, aTag.attr("href"));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseTeamPage(Team t, String url){
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Element gamesTable = doc.getElementsByTag("table").first();
            Elements gameRows = gamesTable.getElementsByTag("tr");
            int i = 0;
            for (Element e: gameRows) {
                if (i < 4){
                    i++;
                    continue;
                }
                String date = e.getElementsByTag("td").first().text();
                Team newTeam = new Team(e.getElementsByTag("td").get(1).text());
                if (searchTeams(newTeam) != null){
                    newTeam = searchTeams(newTeam);
                }
                String location = e.getElementsByTag("td").get(2).text().split("-")[1];
                String points = e.getElementsByTag("td").get(3).text();
                if (points.contains("p.m.") || points.contains("a.m. ") || points.contains("TBA")){
                    continue;
                }
                Game g;
                if (e.getElementsByTag("td").get(3).text().split("-")[1].equalsIgnoreCase("Home")){
                    if (points.split(" ")[0].equalsIgnoreCase("W")){
                        g = new Game(t, newTeam, location, date, Integer.parseInt(points.split(" ")[1].split("-")[0]), Integer.parseInt(points.split(" ")[1].split("-")[1].replace("\u00a0","")), true);
                        if (searchGames(g) == null){
                            games.add(g);
                        }
                        t.setWins(t.getWins() + 1);
                        newTeam.setLosses(newTeam.getLosses() + 1);
                    }
                    else {
                        g = new Game(t, newTeam, location, date, Integer.parseInt(points.split(" ")[1].split("-")[0]), Integer.parseInt(points.split(" ")[1].split("-")[1].replace("\u00a0","")), false);
                        if (searchGames(g) == null){
                            games.add(g);
                        }
                        t.setLosses(t.getLosses() + 1);
                        newTeam.setWins(newTeam.getWins() + 1);
                    }
                }
                else {
                    if (points.split(" ")[0].equalsIgnoreCase("W")){
                        g = new Game(newTeam, t, location, date, Integer.parseInt(points.split(" ")[1].split("-")[0]), Integer.parseInt(points.split(" ")[1].split("-")[1].replace("\u00a0","")), false);
                        if (searchGames(g) == null){
                            games.add(g);
                        }
                        t.setWins(t.getWins() + 1);
                        newTeam.setLosses(newTeam.getLosses() + 1);
                    }
                    else {
                        g = new Game(newTeam, t, location, date, Integer.parseInt(points.split(" ")[1].split("-")[0]), Integer.parseInt(points.split(" ")[1].split("-")[1].replace("\u00a0","")), true);
                        if (searchGames(g) == null){
                            games.add(g);
                        }
                        t.setLosses(t.getLosses() + 1);
                        newTeam.setWins(newTeam.getWins() + 1);
                    }
                }
                System.out.println(g);
                System.out.println(newTeam);
                if (searchTeams(newTeam) == null){
                    teams.add(newTeam);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static Team searchTeams(Team t){
        for (Team e : teams){
            if (t.getName().equalsIgnoreCase(e.getName())){
                return e;
            }
        }
        return null;
    }

    private static Game searchGames(Game g){
        for (Game a : games){
            if (g.isSameGame(a)){
                return a;
            }
        }
        return null;
    }
}
