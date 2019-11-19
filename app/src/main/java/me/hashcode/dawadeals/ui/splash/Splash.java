package me.hashcode.dawadeals.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.model.user.UserDetails;
import me.hashcode.dawadeals.factory.ViewModelFactory;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.login.LoginFragment;
import me.hashcode.dawadeals.ui.login.LoginResult;
import me.hashcode.dawadeals.ui.login.LoginViewModel;

public class Splash extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
    LoginViewModel loginViewModel;
    @Inject
    ViewModelFactory viewModelFactory;
    @Override
    public void saveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    protected void initVaribles() {

    }

    @Override
    public void initViewModels() {

        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel.class);
        observe(loginViewModel);
        loginViewModel.get_loginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                if (loginResult.getError() != null) {
                    forceLoad();
                } else if (loginResult.getNotFound()!=null){
                    startActivity(LoginFragment.class);
                    finishAffinity();
                }else {
                    //setResult(Activity.RESULT_OK);
                    messagesHandler.showToast(UserDetails.readUser().getFullName());
                    home();
                }
            }
        });
    }


    private void forceLoad() {
        messagesHandler.showMessageDialog(R.string.network_fail, R.string.retry_connection, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        loginViewModel.getUserInfo();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
                , false,false);
    }
    @Override
    public void initViews() {
        AndroidInjection.inject(this);
        super.initViews();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                home();
//                if(UserDetails.readUser() == null){
//                startActivity(LoginFragment.class);
//                finishAffinity();
//                }
//                else
//                    loginViewModel.getUserInfo();
            }
        }, 2000);
    }
}
