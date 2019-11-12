package me.hashcode.dawadeals.utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileHelper implements Parcelable {
    public enum FILETYPE{
        MUSIC,IMAGE,IGNORE
    }


    public Integer taOrdImgId;
    @SerializedName("ta_ord_id")
    @Expose
    public String taOrdId;
    public String taOrdImage;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

    @Expose
    @SerializedName("ta_ord_image")
    public String path;
    @Expose
    @SerializedName("ta_ord_img_id")
    public int imageID;
    FILETYPE filetype=FILETYPE.IMAGE;


    protected FileHelper(Parcel in) {
        if (in.readByte() == 0) {
            taOrdImgId = null;
        } else {
            taOrdImgId = in.readInt();
        }
        taOrdId = in.readString();
        taOrdImage = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        path = in.readString();
        imageID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (taOrdImgId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(taOrdImgId);
        }
        dest.writeString(taOrdId);
        dest.writeString(taOrdImage);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(path);
        dest.writeInt(imageID);
    }

    public static final Creator<FileHelper> CREATOR = new Creator<FileHelper>() {
        @Override
        public FileHelper createFromParcel(Parcel in) {
            return new FileHelper(in);
        }

        @Override
        public FileHelper[] newArray(int size) {
            return new FileHelper[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }




    public  FileHelper(){
        filetype=FILETYPE.IMAGE;
        imageID=-1;
    }
    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof FileHelper)
        {
            if(path!=null)
            return imageID==((FileHelper) obj).imageID&&path.equalsIgnoreCase(((FileHelper) obj).path);
        }
        return false;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FILETYPE getFiletype() {
        return filetype;
    }

    public void setFiletype(FILETYPE filetype) {
        this.filetype = filetype;
    }

    public Integer getTaOrdImgId() {
        return taOrdImgId;
    }

    public void setTaOrdImgId(Integer taOrdImgId) {
        this.taOrdImgId = taOrdImgId;
    }

    public String getTaOrdId() {
        return taOrdId;
    }

    public void setTaOrdId(String taOrdId) {
        this.taOrdId = taOrdId;
    }

    public String getTaOrdImage() {
        return taOrdImage;
    }

    public void setTaOrdImage(String taOrdImage) {
        this.taOrdImage = taOrdImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static Creator<FileHelper> getCREATOR() {
        return CREATOR;
    }

}
