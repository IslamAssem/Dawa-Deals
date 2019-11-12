package me.hashcode.dawadeals.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.islam.custom.CustomEditText;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Module;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.model.user.UserDetails;
import me.hashcode.dawadeals.factory.ViewModelFactory;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseFragment;
import me.hashcode.dawadeals.ui.mainActivity.MainActivity;
import me.hashcode.dawadeals.ui.mainActivity.MainActivityGoogleSample;
import me.hashcode.dawadeals.utils.KeyboardUtils;

@Module
public class LoginFragment extends BaseFragment {
    private static final String TAG = "LoginFragment";
    //private lateinit var
    @Inject
    LoginViewModel loginViewModel;
    @BindView(R.id.logo)
    View logo;
    @BindView(R.id.username)
    CustomEditText username;
    @BindView(R.id.password)
    CustomEditText password;
    @BindView(R.id.constraint_layout)
    MotionLayout constraintLayout;

    public LoginFragment() {
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void initViewModel() {
        if (context instanceof BaseActivity)
        ((BaseActivity)context).observe(loginViewModel);
        loginViewModel.getLoginFormState().observe(LoginFragment.this, new Observer<LoginFormState>() {
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
                    ((BaseActivity)context).messagesHandler.showMessageDialog(loginResult.getError());
                } else {
                    ((BaseActivity)context).messagesHandler.showToast(UserDetails.readUser().getFullName());
                    //Complete and destroy login activity once successful
                }
            }
        });
    }
    @BindView(R.id.login)
    View login;
    View loading;
    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    public void initViews() {
         new KeyboardUtils((AppCompatActivity) context) {
            @Override
            public void onShowKeyboard(int keyboardHeight) {
                toggleViews(keyboardHeight);

            }

            @Override
            public void onHideKeyboard() {
                toggleViews(0);
            }
        };
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

    @Override
    public void initData(@NonNull Bundle data) {

    }


    boolean prevState;


    public void toggleViews(int height) {
        boolean hasFocus = height > 0;
        if (prevState == hasFocus)
            return;
        if (context instanceof MainActivity)
            ((MainActivity)context).hideBottom(hasFocus);
        if (context instanceof MainActivityGoogleSample)
            ((MainActivityGoogleSample)context).hideBottom(hasFocus);
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

    @Override
    public String getTAG() {
        return TAG;
    }
}