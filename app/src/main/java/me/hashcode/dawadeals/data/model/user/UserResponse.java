package me.hashcode.dawadeals.data.model.user;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @Expose
    @SerializedName("success")
    int success;
    @Expose
    @SerializedName("data")
    UserDetails userDetails;
    String token;
    String tokenType;
    @Expose
    @SerializedName("message")
    String message;

    public UserDetails getUserDetails() {
        return userDetails;
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

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
