package me.hashcode.dawadeals.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Match {

    @Expose
    @SerializedName("league")
    League league;
    @Expose
    @SerializedName("hall")
    Hall hall;
    @Expose
    @SerializedName("home")
    Team home_team;
    @Expose
    @SerializedName("away")
    Team away_team;
    @Expose
    @SerializedName("match_date")
    String match_date;

    public String getDate(){
        try {

            DateFormat parseFormat = new SimpleDateFormat("dd MMMM yyyy - HH:mm");
            Date date =parseFormat.parse(match_date) ;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public String getTime(){
        try {

            DateFormat parseFormat = new SimpleDateFormat("dd MMMM yyyy - HH:mm");
            Date date =parseFormat.parse(match_date) ;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Team getHome_team() {
        return home_team;
    }

    public void setHome_team(Team home_team) {
        this.home_team = home_team;
    }

    public Team getAway_team() {
        return away_team;
    }

    public void setAway_team(Team away_team) {
        this.away_team = away_team;
    }

    public String getMatch_date() {
        return match_date;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }
}
