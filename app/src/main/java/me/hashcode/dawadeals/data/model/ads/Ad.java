package me.hashcode.dawadeals.data.model.ads;

import java.util.ArrayList;
import java.util.List;

public class Ad {
   String name;
   int quantity;
   int discount;
   String expireDate;
   String price;

    public Ad() {
    }
    public static List<Ad> getDummy(){
        List<Ad>ads = new ArrayList<>();
        Ad ad = null;
        for (int i = 0;i<10;i++){
            ad  =new Ad();
            ad.name = "Pharma";
            ad.quantity = 10000;
            ad.discount = (int) (1.5*(i+1));
            ad.price = "10.75";
            ad.expireDate = "10/22";
            ads.add(ad);
        }
        return ads;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
