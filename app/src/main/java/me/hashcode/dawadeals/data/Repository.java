package me.hashcode.dawadeals.data;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.hashcode.dawadeals.data.model.ads.SponseredAdResponse;
import me.hashcode.dawadeals.data.model.category.CategoryData;
import me.hashcode.dawadeals.data.model.news.NewsResponse;
import me.hashcode.dawadeals.data.model.trade.LatestTransactionsResponse;
import me.hashcode.dawadeals.data.model.user.UserDetails;
import me.hashcode.dawadeals.network.APIRequests;

public class Repository {
    private static UserDetails userDetails;
    APIRequests apiRequests;

    public <T> Single<T> subscribe(Single<T> single) {
        return single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    public Repository(APIRequests apiRequests) {
        this.apiRequests = apiRequests;
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

    public Single<NewsResponse> getNews(int page) {
//        NewsResponse userResponse = new NewsResponse();
//        userResponse.setSuccess(1);
//        userResponse.setNewsList(News.getDummy());
//        return Single.just(userResponse);
        return null;//subscribe(apiRequests.getNews());
    }
    public Single<CategoryData> getCategories() {
        return Single.just(CategoryData.getDummy());
        //      return null;//subscribe(apiRequests.getNews());
    }
    public Single<SponseredAdResponse> getAds(int page) {
        return Single.just(SponseredAdResponse.getDummy());
        //      return null;//subscribe(apiRequests.getNews());
    }
    public Single<LatestTransactionsResponse> getTransactions(int page) {
        return Single.just(LatestTransactionsResponse.getDummy());
        //      return null;//subscribe(apiRequests.getNews());
    }
}
