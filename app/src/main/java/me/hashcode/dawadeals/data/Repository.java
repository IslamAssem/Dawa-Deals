package me.hashcode.dawadeals.data;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.hashcode.dawadeals.data.model.ads.SponseredAdResponse;
import me.hashcode.dawadeals.data.model.category.CategoryData;
import me.hashcode.dawadeals.data.model.news.Notification;
import me.hashcode.dawadeals.data.model.news.NotificationsResponse;
import me.hashcode.dawadeals.data.model.trade.LatestTransactionsResponse;
import me.hashcode.dawadeals.data.model.user.UserDetails;
import me.hashcode.dawadeals.database.DatabaseManager;
import me.hashcode.dawadeals.network.APIRequests;

public class Repository {
    private static UserDetails userDetails;
    APIRequests apiRequests;
    DatabaseManager databaseManager;

    public <T> Single<T> subscribe(Single<T> single) {
        return single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    public Repository(APIRequests apiRequests, DatabaseManager databaseManager) {
        this.apiRequests = apiRequests;
        this.databaseManager = databaseManager;
    }

    public UserDetails getUser() {
        if (userDetails == null)
            userDetails = UserDetails.readUser();
        return userDetails;
    }



    public Single<UserDetails> login(String username, String password) {
//        UserResponse userResponse = new UserResponse();
//        userResponse.setSuccess(1);
//        userResponse.setUserDetails(new UserDetails(1,"islam assem"));
//        return Single.just(userResponse);
        return null;//subscribe(apiRequests.login(username, password));
    }

    public Single<List<SearchEntry>> getSearch() {
        return subscribe(databaseManager.searchDao().select());
    }
    public Single<CategoryData> getCategories() {
        return Single.just(CategoryData.getDummy());
        //      return null;//subscribe(apiRequests.getNotifications());
    }
    public Single<SponseredAdResponse> getAds(int page) {
        return Single.just(SponseredAdResponse.getDummy());
        //      return null;//subscribe(apiRequests.getNotifications());
    }
    public Single<LatestTransactionsResponse> getTransactions(int page) {
        return Single.just(LatestTransactionsResponse.getDummy());
        //      return null;//subscribe(apiRequests.getNotifications());
    }

    public Single<NotificationsResponse> getNotifications(int page) {
        NotificationsResponse userResponse = new NotificationsResponse();
        userResponse.setSuccess(1);
        userResponse.setNotificationList(Notification.getDummy());
        return Single.just(userResponse);
//        return subscribe(apiRequests.getNotifications());
    }

}
