package me.hashcode.dawadeals.ui.splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import me.hashcode.dawadeals.network.APIRequests;
import me.hashcode.dawadeals.ui.base.BaseViewModel;

public class SplashViewModel extends BaseViewModel {

    private MutableLiveData<String> mText;


    @Inject
    public SplashViewModel(APIRequests movieApiService) {
        mText = new MutableLiveData<>();
        mText.setValue("This is Splash activity");
    }

    public LiveData<String> getText() {
        return mText;
    }
}