package me.hashcode.dawadeals.ui.login;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.Repository;
import me.hashcode.dawadeals.data.model.user.UserDetails;
import me.hashcode.dawadeals.network.APIClient;
import me.hashcode.dawadeals.network.APIRequests;
import me.hashcode.dawadeals.ui.base.BaseViewModel;
import me.hashcode.dawadeals.utils.Constants;
import me.hashcode.dawadeals.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModel {
    private Repository loginRepository;
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> _loginResult = new MutableLiveData<>();

    /*
     * We are injecting the MovieDao class
     * and the MovieApiService class to the ViewModel.
     * */

    @Inject
    public LoginViewModel(Repository loginRepository) {
        /* You can see we are initialising the MovieRepository class here */
        this.loginRepository = loginRepository;
    }

    public void login(String username, String password) {
       /* progress.setValue(Utils.getStringRes(R.string.loading));
        // can be launched in a separate asynchronous job
        loginRepository.login(username, password).doOnDispose(this)
                .subscribe(userResponse -> {
                    progress.setValue("");
                    if (userResponse == null || TextUtils.isEmpty(userResponse.getToken()))
                        _loginResult.setValue(new LoginResult(Utils.getStringRes(R.string.incorrect_username_password)));
                    else {

                        Utils.saveSharedPrefrences(Constants.KEY_TOKEN, userResponse.getToken());
                        Utils.saveSharedPrefrences(Constants.KEY_TOKEN_TYPE, userResponse.getTokenType());

                        progress.setValue(Utils.getStringRes(R.string.loading));
                        APIClient.getInstance().getUserDetails().enqueue(new Callback<UserDetails>() {
                            @Override
                            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                                progress.setValue("");
                                if (response == null || response.body() == null || response.body().getId() <= 0)
                                    _loginResult.setValue(new LoginResult(Utils.getStringRes(R.string.incorrect_username_password)));
                                else {
                                    response.body().saveUser();
                                    _loginResult.setValue(
                                            new LoginResult(
                                                    new LoggedInUserView(userResponse)));
                                }
                            }

                            @Override
                            public void onFailure(Call<UserDetails> call, Throwable throwable) {
                                progress.setValue("");
                                _loginResult.setValue(new LoginResult
                                        (Utils.getStringRes(R.string.login_failed)
                                                .concat(" ")
                                                .concat(throwable.getMessage())));
                            }
                        });
                    }
                }, throwable -> {
                    progress.setValue("");
                    if (throwable!=null&&throwable.getMessage()!=null&&throwable.getMessage().contains("401"))
                        _loginResult.setValue(new LoginResult(Utils.getStringRes(R.string.incorrect_username_password)));

                    else _loginResult.setValue(new LoginResult
                            (Utils.getStringRes(R.string.login_failed)
                                    .concat(" ")
                                    .concat(throwable.getMessage())));
                });*/
    }

    public void loginDataChanged(String username, String password) {
       // if (!Utils.isValidPassword(username))
        if (TextUtils.isEmpty(username))
            loginFormState.setValue(LoginFormState.errorUser(R.string.invalid_username));
        else
            if (!Utils.isValidPassword(password))
            loginFormState.setValue(LoginFormState.errorPassword(R.string.invalid_password));
        else loginFormState.setValue(new LoginFormState(true));
    }

    public MutableLiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public MutableLiveData<LoginResult> get_loginResult() {
        return _loginResult;
    }
/*
    public void getUserInfo() {
        progress.setValue(Utils.getStringRes(R.string.loading));
        APIClient.getInstance().getUserDetails().enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                progress.setValue("");
                if (response == null || response.body() == null || response.body().getId() <= 0)
                    _loginResult.setValue(LoginResult.notFound());
                else {
                    response.body().saveUser();
                    _loginResult.setValue(
                            new LoginResult(
                                    new LoggedInUserView(response.body())));
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable throwable) {
                progress.setValue("");
                _loginResult.setValue(new LoginResult
                        (Utils.getStringRes(R.string.login_failed)
                                .concat(" ")
                                .concat(throwable.getMessage())));
            }
        });
    }
*/
}