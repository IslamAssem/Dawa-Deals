package me.hashcode.dawadeals.ui.home;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.Repository;
import me.hashcode.dawadeals.data.model.ads.Ad;
import me.hashcode.dawadeals.data.model.trade.Transaction;
import me.hashcode.dawadeals.ui.base.BaseViewModel;
import me.hashcode.dawadeals.utils.Utils;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public class HomeViewModel extends BaseViewModel {

    private final Repository repository;
     public MutableLiveData<List<Ad>> adsResponseLiveData;
     public MutableLiveData<List<Transaction>> transactionsResponseLiveData;
    private int page;

    @Inject
    public HomeViewModel(Repository repository) {
         adsResponseLiveData = new MutableLiveData<>();
        transactionsResponseLiveData = new MutableLiveData<>();
         this.repository = repository;
    }

    public void getHomeAds(){
        progress.setValue(Utils.getStringRes(R.string.loading));
        repository.getAds(page).doOnDispose(this)
                .subscribe(sponseredAdResponse -> {
                    progress.setValue(null);
                    adsResponseLiveData.setValue(sponseredAdResponse.getAds());
                },throwable -> {
                    progress.setValue(null);
                    toast.setValue(Utils.getStringRes(R.string.network_fail)+" : "+throwable.getMessage());
                    adsResponseLiveData.setValue(null);
                });
    }

    public void getLatestTranactions() {

        progress.setValue(Utils.getStringRes(R.string.loading));
        repository.getTransactions(page).doOnDispose(this)
                .subscribe(transactionsResponse -> {
                    progress.setValue(null);
                    transactionsResponseLiveData.setValue(transactionsResponse.getTransactions());
                },throwable -> {
                    progress.setValue(null);
                    toast.setValue(Utils.getStringRes(R.string.network_fail)+" : "+throwable.getMessage());
                    transactionsResponseLiveData.setValue(null);
                });
    }
}