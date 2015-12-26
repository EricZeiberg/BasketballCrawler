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
                teams.add(t);
                parseTeamPage(t, aTag.attr("href"));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Team parseTeamPage(Team t, String url){
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Element gamesTable = doc.getElementsByTag("table").first();
            Elements gameRows = gamesTable.getElementsByTag("tr");
            int i = 0;
            for (Element e: gameRows) {
                if (i < 3){
                    i++;
                    continue;
                }
                String date = e.getElementsByTag("td").first().text();
                Team newTeam = new Team(e.getElementsByTag("td").get(2).text());
                String location = e.getElementsByTag("td").get(3).text().split("-")[2];
                String points = e.getElementsByTag("td").get(4).text();
                Game g;
                if (e.getElementsByTag("td").get(3).text().split("-")[1].equalsIgnoreCase("Home")){
                    
                }
                if (searchTeams(newTeam) == null){
                    teams.add(newTeam);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
}
