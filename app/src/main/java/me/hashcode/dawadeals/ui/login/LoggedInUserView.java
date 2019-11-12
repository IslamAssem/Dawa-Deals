package me.hashcode.dawadeals.ui.login;

import me.hashcode.dawadeals.data.model.user.UserDetails;

/**
 * User details post authentication that is exposed to the UI
 */
 public class LoggedInUserView{
    UserDetails userDetails;

    public LoggedInUserView(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetails getDisplayName() {
        return userDetails;
    }

    public void setDisplayName(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    //... other data fields that may be accessible to the UI
}