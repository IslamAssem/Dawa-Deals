package me.hashcode.dawadeals.ui.notifications;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.Repository;
import me.hashcode.dawadeals.data.model.news.Notification;
import me.hashcode.dawadeals.ui.base.BaseViewModel;
import me.hashcode.dawadeals.utils.Utils;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public class NotificationsViewModel extends BaseViewModel {

    private final Repository repository;
      private int page;
    private int current = -1;
    private int currentTotal;
    private MutableLiveData<List<Notification>> notificationsLiveData;
    private boolean hasMoreNews;
    @Inject
    public NotificationsViewModel(Repository repository) {
         notificationsLiveData = new MutableLiveData<>();
          this.repository = repository;
    }



    public void getNotifications() {
        if (current>=0)
            if (current>=currentTotal){
                hasMoreNews=(false);
                return;
            }
        disposables.add(repository.getNotifications(page).doOnDispose(this).subscribe(
                notificationsResponse -> {
                    if (notificationsResponse.getSuccess() == 0)
                        notificationsLiveData.setValue(null);
                    else {
                        List<Notification> notificationList = notificationsLiveData.getValue();
                        if (notificationsResponse.getNotificationList() != null) {
                            if (notificationList == null)
                                notificationList = new ArrayList<>();
                            notificationList.addAll(notificationsResponse.getNotificationList());
                            notificationsLiveData.setValue(notificationList);
                            current = notificationsResponse.getTo();
                            currentTotal = notificationsResponse.getTotal();
                            if (current>=currentTotal)
                                hasMoreNews = (false);
                        }
                    }
                }, throwable -> {
                    message.setValue(Utils.getStringRes(R.string.network_fail) + " " + throwable.getMessage());
                }));

    }

    public MutableLiveData<List<Notification>> getNotificationsLiveData() {
        return notificationsLiveData;
    }
}