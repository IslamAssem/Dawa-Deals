package me.hashcode.dawadeals.ui.login;

/**
 * Data validation state of the login form.
 */
public class LoginFormState{
    private Integer usernameError;
    private Integer passwordError;
    private boolean isDataValid;

    public LoginFormState() {
    }

    public static LoginFormState errorUser(int usernameError){
        LoginFormState loginFormState = new LoginFormState();
        loginFormState.usernameError=usernameError;
        return loginFormState;
    }
    public static LoginFormState errorPassword(int passwordError){
        LoginFormState loginFormState = new LoginFormState();
        loginFormState.passwordError=passwordError;
        return loginFormState;
    }
    public LoginFormState(Boolean isDataValid) {
        this.isDataValid = isDataValid;
    }

    public Integer getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(Integer usernameError) {
        this.usernameError = usernameError;
    }

    public Integer getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(Integer passwordError) {
        this.passwordError = passwordError;
    }

    public boolean getDataValid() {
        return isDataValid;
    }

    public void setDataValid(Boolean dataValid) {
        isDataValid = dataValid;
    }
}
