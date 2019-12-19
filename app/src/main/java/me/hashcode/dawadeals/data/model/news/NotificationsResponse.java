package me.hashcode.dawadeals.data.model.news;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.hashcode.dawadeals.utils.Utils;

public class NotificationsResponse {
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
    List<Notification> notificationList;
    @Expose
    @SerializedName("notifications")
    Container container;

    @Expose
    @SerializedName("message")
    String message;

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

    public List<Notification> getNotificationList() {
        if (container!=null&& notificationList ==null)
            notificationList = container.getNotificationList();
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
        if (!Utils.isEmpty(notificationList))
        total = notificationList.size();
        to = notificationList.size();
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Container{
        @Expose
        @SerializedName("data")
        List<Notification> notificationList;

        public List<Notification> getNotificationList() {
            return notificationList;
        }

        public void setNotificationList(List<Notification> notificationList) {
            this.notificationList = notificationList;
        }
    }
}
