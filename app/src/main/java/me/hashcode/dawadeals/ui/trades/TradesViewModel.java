package me.hashcode.dawadeals.ui.trades;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.Repository;
import me.hashcode.dawadeals.data.model.trade.Transaction;
import me.hashcode.dawadeals.ui.base.BaseViewModel;
import me.hashcode.dawadeals.utils.Utils;

public class TradesViewModel extends BaseViewModel {

    private final Repository repository;
    private int page;
    private MutableLiveData<List<Transaction>> tradesLiveData;
    @Inject
    public TradesViewModel(Repository repository) {
        this.repository = repository;
        tradesLiveData = new MutableLiveData<>();
     }


    public void getLatestTrades() {

        progress.setValue(Utils.getStringRes(R.string.loading));
        repository.getTransactions(page).doOnDispose(this)
                .subscribe(transactionsResponse -> {
                    progress.setValue(null);
                    tradesLiveData.setValue(transactionsResponse.getTransactions());
                },throwable -> {
                    progress.setValue(null);
                    toast.setValue(Utils.getStringRes(R.string.network_fail)+" : "+throwable.getMessage());
                    tradesLiveData.setValue(null);
                });
    }

    public MutableLiveData<List<Transaction>> getTradesLiveData() {
        return tradesLiveData;
    }
}