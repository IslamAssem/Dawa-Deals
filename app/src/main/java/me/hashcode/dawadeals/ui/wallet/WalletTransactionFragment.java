package me.hashcode.dawadeals.ui.wallet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.islam.custom.patternedTextWatcher.PatternedTextWatcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseFragment;
import me.hashcode.dawadeals.ui.mainActivity.MainActivity;
import me.hashcode.dawadeals.ui.mainActivity.MainActivityGoogleSample;
import me.hashcode.dawadeals.utils.Constants;
import me.hashcode.dawadeals.utils.KeyboardUtils;
import me.hashcode.dawadeals.utils.Utils;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;

public class WalletTransactionFragment extends BaseFragment {

    private static final String TAG = "WalletTransactionFragment";

    @BindView(R.id.expire_date)
    EditText expire_date;
    @Inject
    WalletViewModel walletViewModel;

    @BindView(R.id.withdraw)
    View withdraw;
    @BindView(R.id.deposit)
    View deposit;
    @BindView(R.id.card)
    View card;
    @BindView(R.id.fawry)
    View fawry;
    @BindView(R.id.bank)
    View bank;
    int prevState = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_wallet_transaction, container, false);
    }

    @Override
    public void initViews() {
        if (context instanceof MainActivityGoogleSample)
            ((MainActivityGoogleSample) context).setTextTitle(null,false,false);
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("##/##")
                .fillExtraCharactersAutomatically(true)
                .deleteExtraCharactersAutomatically(true)
                .specialChar("#")
                .respectPatternLength(true)
                .saveAllInput(false)
                .build();
         expire_date.addTextChangedListener(patternedTextWatcher);
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
        if (hasFocus)
            prevState = 1;
        else prevState = -1;
    }
    @Override
    public void initVariables() {

    }

    @Override
    public void initData(@NonNull Bundle data) {
        int x = data.getInt(Constants.DATA);
        if (x == 0)
            withdraw.performClick();
        else deposit.performClick();


    }

    @Override
    public void initViewModel() {
        if (context instanceof BaseActivity)
            ((BaseActivity) context).observe(walletViewModel);
        walletViewModel.getWalletTradesLiveData().observe(this, transactions -> {


        });
    }
    @Override
    public String getTAG() {
        return WalletTransactionFragment.TAG;
    }

    @OnClick(R.id.show_help)
    public void showHelp(View view){
        GuideView guideView = new GuideView.Builder(context)
                .setTitle(null)
                .setContentText(Utils.getStringRes(R.string.wallet_help))
                .setGravity(Gravity.auto)
                .setTargetView(view)
                .setContentTextSize(12)
                .setDismissType(DismissType.outside) //optional - default dismissible by TargetView
                //.setGuideListener(view -> showImages())
                .build();
//        guideView.setBackground();
        guideView.getMessageView().setBackground(Utils.getDrawableRes(context,R.drawable
                .ic_rectangle_wallet_help));
        guideView.getMessageView().setColor(Utils.getColorRes(R.color.colorPrimary));
        guideView.getMessageView().setContentTextColor(Utils.getColorRes(R.color.white));

        guideView.show();

    }
    @OnClick(R.id.withdraw)
    public void withdraw(){
        deposit.setSelected(false);
        withdraw.setSelected(true);
    }
    @OnClick(R.id.deposit)
    public void deposit(){
        deposit.setSelected(true);
        withdraw.setSelected(false);
    }
    @OnClick(R.id.bank)
    public void bank(){
        bank.setSelected(true);
        fawry.setSelected(false);
        card.setSelected(false);
    }
    @OnClick(R.id.fawry)
    public void fawry(){
        bank.setSelected(false);
        fawry.setSelected(true);
        card.setSelected(false);
    }
    @OnClick(R.id.card)
    public void card(){
        bank.setSelected(false);
        fawry.setSelected(false);
        card.setSelected(true);
    }
}