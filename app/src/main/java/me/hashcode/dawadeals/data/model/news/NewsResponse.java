package me.hashcode.dawadeals.data.model.news;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {
    @Expose
    @SerializedName("success")
    int success;
    @Expose
    @SerializedName("total")
    int total;
    @Expose
    @SerializedName("to")
    int to;
    @Expose
    @SerializedName("data")
    List<News> newsList;
    @Expose
    @SerializedName("store")
    Container container;

    @Expose
    @SerializedName("message")
    String message;

    public class Container{
        @Expose
        @SerializedName("data")
        List<News> newsList;

        public List<News> getNewsList() {
            return newsList;
        }

        public void setNewsList(List<News> newsList) {
            this.newsList = newsList;
        }
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<News> getNewsList() {
        if (container!=null&&newsList==null)
            newsList = container.getNewsList();
        return newsList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
