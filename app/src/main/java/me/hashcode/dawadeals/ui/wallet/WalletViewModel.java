package me.hashcode.dawadeals.ui.wallet;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.Repository;
import me.hashcode.dawadeals.data.model.trade.Transaction;
import me.hashcode.dawadeals.ui.base.BaseViewModel;
import me.hashcode.dawadeals.utils.Utils;

public class WalletViewModel extends BaseViewModel {

    private final Repository repository;
    private int page;
    private MutableLiveData<List<Transaction>> walletTradesLiveData;
    @Inject
    public WalletViewModel(Repository repository) {
        this.repository = repository;
        walletTradesLiveData = new MutableLiveData<>();
     }


    public void getWalletTrades() {

        progress.setValue(Utils.getStringRes(R.string.loading));
        repository.getTransactions(page).doOnDispose(this)
                .subscribe(transactionsResponse -> {
                    progress.setValue(null);
                    walletTradesLiveData.setValue(transactionsResponse.getTransactions());
                },throwable -> {
                    progress.setValue(null);
                    toast.setValue(Utils.getStringRes(R.string.network_fail)+" : "+throwable.getMessage());
                    walletTradesLiveData.setValue(null);
                });
    }

    public MutableLiveData<List<Transaction>> getWalletTradesLiveData() {
        return walletTradesLiveData;
    }
}