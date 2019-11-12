package me.hashcode.dawadeals.data.model.user;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import me.hashcode.dawadeals.utils.Constants;
import me.hashcode.dawadeals.utils.Utils;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class UserDetails {
    @Expose
    @SerializedName("referee_id")
    private int id;
    @Expose
    @SerializedName("referee_username")
    private String referee_username;
    @Expose
    @SerializedName("referee_mobile")
    private String phone;
    @Expose
    @SerializedName("referee_email")
    private String email;
    @Expose
    @SerializedName("referee_fullname")
    private String fullName;
    @Expose
    @SerializedName("referee_address")
    private String address;
    @Expose
    @SerializedName("referee_birthday")
    private String dateOfBirth;
    @Expose
    @SerializedName("referee_nationl_identity")
    private String nationlId;
    @Expose
    @SerializedName("referee_identity")
    private String identity;
    @Expose
    @SerializedName("referee_image")
    private String image;
    @Expose
    @SerializedName("referee_type")
    private String type;
    @Expose
    @SerializedName("access_token")
    private String token;
    @Expose
    @SerializedName("token_type")
    private String tokenType;
    public UserDetails() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReferee_username() {
        return referee_username;
    }

    public void setReferee_username(String referee_username) {
        this.referee_username = referee_username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationlId() {
        return nationlId;
    }

    public void setNationlId(String nationlId) {
        this.nationlId = nationlId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public synchronized void saveUser(){
        Gson gson=new Gson();
        try{
            String json=gson.toJson(this);
            if(TextUtils.isEmpty(json))return;
            Utils.saveSharedPrefrences(Constants.KEY_USER,json);
        }catch (Exception ignored)
        {
        }
    }
    public static synchronized void removeUser(){
        Utils.saveSharedPrefrences("Gm","0");
        Utils.saveSharedPrefrences("GSteps","0");
        Utils.saveSharedPrefrences("introoo","0");
        try{
            String json="";
            Utils.saveSharedPrefrences(Constants.KEY_USER,json);
            Utils.saveSharedPrefrences(Constants.KEY_ADDRESS,json);
            Utils.saveSharedPrefrences(Constants.KEY_TOKEN,json);
            Utils.saveSharedPrefrences("PREF_UNIQUE_ID",json);
        }catch (Exception ignored)
        {
        }
    }
    @NonNull
    public static UserDetails readUser(){
        Gson gson=new Gson();
        try{
            String json=Utils.getSharedPrefrences(Constants.KEY_USER,"");
            if(!TextUtils.isEmpty(json))
                return gson.fromJson(json,UserDetails.class);
        }catch (Exception ignored)
        {
        }
        return null;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof UserDetails)
            return id == ((UserDetails) obj).id ;
                    //&&TextUtils.equals(username, ((UserDetails) obj).username);
        return false;
    }
}