package me.hashcode.dawadeals.data.model.ads;

import java.util.List;

public class SponseredAdResponse {
    int sucess;
    String message;
    List<Ad> ads;

    public SponseredAdResponse() {
    }

    public SponseredAdResponse(int sucess, String message, List<Ad> ads) {
        this.sucess = sucess;
        this.message = message;
        this.ads = ads;
    }

    public static SponseredAdResponse getDummy() {
        return new SponseredAdResponse(1,"return data",Ad.getDummy());
    }

    public int getSucess() {
        return sucess;
    }

    public void setSucess(int sucess) {
        this.sucess = sucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }
}
