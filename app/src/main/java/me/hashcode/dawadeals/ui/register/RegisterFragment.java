package me.hashcode.dawadeals.ui.register;

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
import androidx.fragment.app.FragmentActivity;

import com.islam.custom.CustomEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseFragment;
import me.hashcode.dawadeals.ui.login.LoginViewModel;
import me.hashcode.dawadeals.ui.mainActivity.MainActivity;
import me.hashcode.dawadeals.ui.mainActivity.MainActivityGoogleSample;
import me.hashcode.dawadeals.utils.KeyboardUtils;
import me.hashcode.dawadeals.utils.Utils;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

@Module
public class RegisterFragment extends BaseFragment {
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
    @BindView(R.id.confirm_password)
    CustomEditText passwordConfirm;
    @BindView(R.id.constraint_layout)
    MotionLayout constraintLayout;
    View loading;
    int prevState;
    TextWatcher tw;
    private boolean passwordVisibility = true;

    public RegisterFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void initViewModel() {
        if (context instanceof BaseActivity)
            ((BaseActivity) context).observe(loginViewModel);

    }

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
        prevState = 0;
        password.addTextChangedListener(getTextWatcher());
        passwordConfirm.addTextChangedListener(getTextWatcher());
        username.addTextChangedListener(getTextWatcher());

        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                password.requestFocus();
                return false;
            }
        });
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                passwordConfirm.requestFocus();
                return false;
            }
        });
        passwordConfirm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                getView().findViewById(R.id.register).performClick();
                return false;
            }
        });
        togglePassword(getView().findViewById(R.id.password_eye));
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initData(@NonNull Bundle data) {

    }

    @OnClick(R.id.password_eye)
    public void togglePassword(ImageView password_eye) {
        passwordVisibility = Utils.showPasswordCheckBoxListener(passwordVisibility, passwordConfirm, password, password_eye);

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
            ((MainActivity) context).hideBottom(hasFocus);
        if (context instanceof MainActivityGoogleSample)
            ((MainActivityGoogleSample) context).hideBottom(hasFocus);
        constraintLayout.transitionToStart();
        constraintLayout.transitionToEnd();
        if (hasFocus)
            prevState = 1;
        else prevState = -1;
    }

    public TextWatcher getTextWatcher() {
        if (tw == null)
            tw = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    register();
                }
            };
        return tw;

    }

    @OnClick({R.id.register})
    public void register() {
        findNavController(this).navigate(R.id.action_account);

    }
    @OnClick({R.id.already_have_account, R.id.login})
    public void login() {
        FragmentActivity act = getActivity();
        if (act !=null)
        getActivity().onBackPressed();
        else findNavController(this).navigate(R.id.action_login);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}