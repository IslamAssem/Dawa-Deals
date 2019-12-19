package me.hashcode.dawadeals.data.model.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Notification implements Parcelable {

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
    static int i = 0;
    @Expose
    @SerializedName("new_id")
    int id;
                @Expose
    @SerializedName("new_image")
    String image;
    @Expose
    @SerializedName("new_title")
    String title;
    @Expose
    @SerializedName("new_description")
    String description;
    @Expose
    @SerializedName("date")
    String date;

    public Notification(){}

    public Notification(String image, String title) {
        this.image = image;
        this.title = title;
        this.description = title;
        this.date = title;
    }

    protected Notification(Parcel in) {
        id = in.readInt();
        image = in.readString();
        title = in.readString();
        description = in.readString();
        date = in.readString();
    }

    public static List<Notification> getDummy() {
        List<Notification> notifications = new ArrayList<>();
        for( int j = 0;j<10;j++,i++)
            notifications.add(new Notification("","Notification : "+i));
        return notifications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
    }
}
