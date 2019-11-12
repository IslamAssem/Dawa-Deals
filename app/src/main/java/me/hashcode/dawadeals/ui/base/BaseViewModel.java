package me.hashcode.dawadeals.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import timber.log.Timber;


public abstract class BaseViewModel extends ViewModel  implements Action {

    protected MutableLiveData<String> progress;
    protected MutableLiveData<String> message;
    protected MutableLiveData<String> toast;
    protected MutableLiveData<Boolean> booleanMutableLiveData;
    private static MutableLiveData<Boolean> forceLogout ;


    public CompositeDisposable disposables;
    public BaseViewModel() {
        progress = new MutableLiveData<>();
        message = new MutableLiveData<>();
        disposables = new CompositeDisposable();
        toast = new MutableLiveData<>();

    }

    public MutableLiveData<Boolean> getBooleanMutableLiveData() {
        return booleanMutableLiveData;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public MutableLiveData<String> getProgress() {
        return progress;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public static MutableLiveData<Boolean> getForceLogout() {
        if(forceLogout==null)
            forceLogout = new MutableLiveData<>();
        return forceLogout;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
//
//    public MutableLiveData<HomeDataResponse> getStartUpResponseMutableLiveData() {
//        return startUpResponseMutableLiveData;
//    }

    @Override
    public void run() {
        Timber.e("do on dispose");

    }
//
//    private Single<HomeDataResponse> startUpP(){
//        UserDetails userDetails=UserDetails.readUser();
//        if(userDetails==null)
//            return APIClient.getInstance().startUp()
//                    .subscribeOn(Schedulers.io()).observeOn(
//                            AndroidSchedulers.mainThread()
//                    );
//        else return APIClient.getInstance().startUp(userDetails.getUser_id())
//                .subscribeOn(Schedulers.io()).observeOn(
//                        AndroidSchedulers.mainThread()
//                );
//    }
//
//    public void startUp() {
//        progress.setValue(Utils.getStringRes(R.string.loading));
//        disposables.add(startUpP().doOnDispose(() -> progress.setValue(""))
//                .doOnDispose(() -> progress.setValue(MessagesHandler.HIDEDIALOG))
//                .subscribe(startUpResponse -> {
//                            progress.setValue("");
//                            startUpResponseMutableLiveData.setValue(startUpResponse);
//                        }
//                        , throwable -> {
//                            progress.setValue("");
//                            startUpResponseMutableLiveData.setValue(null);
//                        }));
//    }
}
