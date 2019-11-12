package me.hashcode.dawadeals.ui.categories;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.Repository;
import me.hashcode.dawadeals.data.model.ads.Ad;
import me.hashcode.dawadeals.data.model.category.CategoryData;
import me.hashcode.dawadeals.data.model.category.CategoryDetails;
import me.hashcode.dawadeals.data.model.trade.Transaction;
import me.hashcode.dawadeals.ui.base.BaseViewModel;
import me.hashcode.dawadeals.utils.Utils;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public class CategoriesViewModel extends BaseViewModel {

    private final Repository repository;
     public MutableLiveData<CategoryData> categoriesResponseLiveData;
     private int page;

    @Inject
    public CategoriesViewModel(Repository repository) {
        categoriesResponseLiveData = new MutableLiveData<>();
          this.repository = repository;
    }

    public void getCategories(){
        progress.setValue(Utils.getStringRes(R.string.loading));
        repository.getCategories().doOnDispose(this)
                .subscribe(categoriesResponse -> {
                    progress.setValue(null);
                    categoriesResponseLiveData.setValue(categoriesResponse);
                },throwable -> {
                    progress.setValue(null);
                    toast.setValue(Utils.getStringRes(R.string.network_fail)+" : "+throwable.getMessage());
                    categoriesResponseLiveData.setValue(null);
                });
    }

}