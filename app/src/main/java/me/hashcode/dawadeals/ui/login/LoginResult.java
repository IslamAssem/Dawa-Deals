package me.hashcode.dawadeals.ui.login;

/**
 * Authentication result : success (user details) or error message.
 */
public class LoginResult{
    LoggedInUserView success;
    String error;
    String notFound;

    public LoginResult() {
    }

    public LoginResult(LoggedInUserView success) {
        this.success = success;
    }
    public static LoginResult notFound(){
        LoginResult loginResult = new LoginResult();
        loginResult.notFound = "user not found";
        return loginResult;
    }
    public LoginResult(String error) {
        this.error = error;
    }

    public LoggedInUserView getSuccess() {
        return success;
    }

    public void setSuccess(LoggedInUserView success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public String getNotFound() {
        return notFound;
    }

    public void setNotFound(String notFound) {
        this.notFound = notFound;
    }

    public void setError(String error) {
        this.error = error;
    }
}
