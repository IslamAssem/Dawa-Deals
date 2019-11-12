package me.hashcode.dawadeals.data.model.trade;

import androidx.annotation.Nullable;

import com.islam.custom.stickyHeader.StickyMainData;

import java.util.ArrayList;
import java.util.List;

public class Transaction implements StickyMainData {
    public static final int TYPE_BUY = 2;
    int id;
   String name;
   int quantity;
   int discount;
   String expireDate;
   String price;
   int type;
   String buyer;
   String seller;
   String category;
   String marketPrice;
   String partDeal;

    public Transaction() {

    }
    public static List<Transaction> getDummy(){
        List<Transaction>ads = new ArrayList<>();
        Transaction ad = null;
        for (int i = 0;i<100;i++){
            ad  =new Transaction();
            ad.name = "Pharma";
            ad.id = i;
            ad.quantity = 10000;
            ad.discount = i;
            ad.price = "10.75";
            ad.expireDate = "10/22";
            ad.type = i%3;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getPartDeal() {
        return partDeal;
    }

    public void setPartDeal(String partDeal) {
        this.partDeal = partDeal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Transaction)
            return ((Transaction) obj).id==id;
        return false;
    }
}
