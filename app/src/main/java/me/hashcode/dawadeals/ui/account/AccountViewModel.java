package me.hashcode.dawadeals.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import me.hashcode.dawadeals.ui.base.BaseViewModel;

public class AccountViewModel extends BaseViewModel {

    private MutableLiveData<String> mText;

    public AccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}