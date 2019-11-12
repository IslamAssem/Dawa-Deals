package me.hashcode.dawadeals.ui.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.adapters.TransactionsAdapter;
import me.hashcode.dawadeals.adapters.WalletTradesAdapter;
import me.hashcode.dawadeals.data.model.trade.Transaction;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseFragment;
import me.hashcode.dawadeals.ui.categories.CategoriesViewModel;
import me.hashcode.dawadeals.utils.Utils;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;

public class WalletFragment extends BaseFragment {

    private static final String TAG = "WalletFragment";

    @BindView(R.id.wallet_trades)
    RecyclerView walletTrades;
    @Inject
    WalletViewModel walletViewModel;
    private WalletTradesAdapter transactionsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void initViews() {
        walletTrades.setLayoutManager(new LinearLayoutManager(context));walletTrades.setAdapter(transactionsAdapter = new WalletTradesAdapter(new OnItemClickListener(){
            @Override
            public void OnItemClick(Object data, View view, int position) {
                super.OnItemClick(data, view, position);
            }
        }));
        walletViewModel.getWalletTrades();

    }

    @Override
    public void initData(@NonNull Bundle data) {

    }

    @Override
    public void initViewModel() {
        if (context instanceof BaseActivity)
            ((BaseActivity) context).observe(walletViewModel);
        walletViewModel.getWalletTradesLiveData().observe(this, transactions -> {
                 if (transactions == null)
                    showTransactionsError();
                else if (transactions.size()==0)
                    showNoTransactions();
                else showTransactions(transactions);

        });
    }
    private void showTransactionsError() {

    }

    private void showNoTransactions() {

    }

    private void showTransactions(List<Transaction> transactions) {
            transactionsAdapter.add(transactions);
    }

    @Override
    public String getTAG() {
        return WalletFragment.TAG;
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
}