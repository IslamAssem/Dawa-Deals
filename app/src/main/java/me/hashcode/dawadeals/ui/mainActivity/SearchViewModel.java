package me.hashcode.dawadeals.ui.mainActivity;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import me.hashcode.dawadeals.data.Repository;
import me.hashcode.dawadeals.data.SearchEntry;
import me.hashcode.dawadeals.ui.base.BaseViewModel;
import timber.log.Timber;

public class SearchViewModel extends BaseViewModel {

    private final Repository repository;
    public MutableLiveData<List<SearchEntry>> searchLiveData = new MutableLiveData<>();

    @Inject
    public SearchViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getSearchHistory() {
        repository.getSearch().subscribe(searchEntries -> {
            Timber.tag("SearchViewModel").e("getSearch");
            searchLiveData.setValue(searchEntries);
        }, throwable -> {
            Timber.tag("SearchViewModel").e("getSearch" + throwable.getMessage());
        });
    }
}
