package me.hashcode.dawadeals.data.model.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class News implements Parcelable {

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
    static int i = 0;
    public News(){}

    public News(String image, String title) {
        this.image = image;
        this.title = title;
    }

    protected News(Parcel in) {
        id = in.readInt();
        image = in.readString();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public static List<News> getDummy() {
        List<News>news = new ArrayList<>();
        for( int j = 0;j<10;j++,i++)
            news.add(new News("","News : "+i));
        return news;
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
    }
}
