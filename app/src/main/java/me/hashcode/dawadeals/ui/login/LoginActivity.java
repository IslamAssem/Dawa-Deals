package me.hashcode.dawadeals.ui.login;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.islam.custom.CustomEditText;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Module;
import dagger.android.AndroidInjection;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.model.user.UserDetails;
import me.hashcode.dawadeals.factory.ViewModelFactory;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.utils.KeyboardUtils;

@Module
public class LoginActivity extends BaseActivity {
    //private lateinit var
    LoginViewModel loginViewModel;
    @BindView(R.id.logo)
    View logo;
    @BindView(R.id.username)
    CustomEditText username;
    @BindView(R.id.password)
    CustomEditText password;
    @BindView(R.id.constraint_layout)
    MotionLayout constraintLayout;

    public LoginActivity() {
    }

    @Override
    public void saveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initVaribles() {

    }

    @Override
    public void initViewModels() {
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel.class);
        observe(loginViewModel);
        loginViewModel.getLoginFormState().observe(LoginActivity.this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(LoginFormState loginFormState) {

                // disable login button unless both username / password is valid
                login.setEnabled(loginFormState.getDataValid());

                if (loginFormState.getUsernameError() != null) {
                    username.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    password.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.get_loginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {

                loading.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    messagesHandler.showMessageDialog(loginResult.getError());
                } else {
                    messagesHandler.showToast(UserDetails.readUser().getFullName());
                    //Complete and destroy login activity once successful
                    home();
                }
            }
        });
    }
    FloatingActionButton login;
    View loading;
    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    public void initViews() {
        AndroidInjection.inject(this);
        super.initViews();
         new KeyboardUtils(activity) {
            @Override
            public void onShowKeyboard(int keyboardHeight) {
                toggleViews(keyboardHeight);

            }

            @Override
            public void onHideKeyboard() {
                toggleViews(0);
            }
        };
        login = findViewById(R.id.login);
        loading = findViewById(R.id.loading);
        login.setEnabled(false);

        password.addTextChangedListener(getTextWatcher());
        username.addTextChangedListener(getTextWatcher());

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                loginViewModel.login(
                        username.getText().toString(),
                        password.getText().toString()
                );

                return false;
            }
        });
        login.setOnClickListener(view -> {

            loading.setVisibility(View.VISIBLE);
            loginViewModel.login(username.getText().toString(), password.getText().toString());
        });
    }
    boolean prevState;


    public void toggleViews(int height) {
        boolean hasFocus = height > 0;
        if (prevState == hasFocus)
            return;
        constraintLayout.transitionToStart();
        constraintLayout.transitionToEnd();
        prevState = hasFocus;
    }
    TextWatcher tw;
    public TextWatcher getTextWatcher(){
        if (tw==null)
            tw = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    loginViewModel.loginDataChanged(
                            username.getText().toString(),
                            password.getText().toString()
                    );
                }
            };
        return tw;

    }
}