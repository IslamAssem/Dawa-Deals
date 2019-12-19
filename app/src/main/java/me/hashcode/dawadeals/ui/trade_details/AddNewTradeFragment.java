package me.hashcode.dawadeals.ui.trade_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import butterknife.BindView;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseFragment;
import me.hashcode.dawadeals.ui.trades.TradesViewModel;
import me.hashcode.dawadeals.utils.Utils;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;

public class AddNewTradeFragment extends BaseFragment {

    private static final String TAG = "TradeDetailsPage";

    @Inject
    TradesViewModel tradesViewModel;
    @BindView(R.id.buying_background)
    View buying_background;
    private OnItemClickListener onItemClickListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_add_trade, container, false);
    }

    @Override
    public void initViews() {
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initData(@NonNull Bundle data) {

    }

    @Override
    public void initViewModel() {
        if (context instanceof BaseActivity)
            ((BaseActivity) context).observe(tradesViewModel);
    }

    @Override
    public String getTAG() {
        return AddNewTradeFragment.TAG;
    }

    //@OnClick(R.id.show_help)
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

    public void setListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}