package me.hashcode.dawadeals.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.Observer;

import com.islam.custom.CustomEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.model.user.UserDetails;
import me.hashcode.dawadeals.factory.ViewModelFactory;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseFragment;
import me.hashcode.dawadeals.ui.mainActivity.MainActivity;
import me.hashcode.dawadeals.ui.mainActivity.MainActivityGoogleSample;
import me.hashcode.dawadeals.utils.KeyboardUtils;
import me.hashcode.dawadeals.utils.Utils;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

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
    int prevState;

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
    private boolean passwordVisibility = true;

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
        getView().post(new Runnable() {
            @Override
            public void run() {

            }
        });
        prevState = 0;
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
        togglePassword(getView().findViewById(R.id.password_eye));
    }

    @Override
    public void initData(@NonNull Bundle data) {

    }

    @Override
    public void initVariables() {

    }

    @OnClick(R.id.password_eye)
    public void togglePassword(ImageView password_eye) {
        passwordVisibility = Utils.showPasswordCheckBoxListener(passwordVisibility, password, password_eye);

    }

    public void toggleViews(int height) {
        if (!isRunning)
            return;
        boolean hasFocus = height > 0;
        if (prevState == 1 && hasFocus)
            return;
        if (prevState == -1 && !hasFocus)
            return;
        if (prevState == 0 && height == 0) {
            prevState = -1;
            return;
        }
        Log.e("toggleViews", "height hasFocus " + hasFocus + " : " + height);
        if (context instanceof MainActivity)
            ((MainActivity)context).hideBottom(hasFocus);
        if (context instanceof MainActivityGoogleSample)
            ((MainActivityGoogleSample)context).hideBottom(hasFocus);
        constraintLayout.transitionToStart();
        constraintLayout.transitionToEnd();
        if (hasFocus)
            prevState = 1;
        else prevState = -1;
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

    @OnClick({R.id.dont_have_account, R.id.sign_up})
    public void register() {
        findNavController(this).navigate(R.id.action_register);
    }
    @Override
    public String getTAG() {
        return TAG;
    }
}