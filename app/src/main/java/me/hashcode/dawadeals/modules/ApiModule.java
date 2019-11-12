package me.hashcode.dawadeals.modules;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.hashcode.dawadeals.data.Repository;
import me.hashcode.dawadeals.network.APIClient;
import me.hashcode.dawadeals.network.APIRequests;

@Module
public class ApiModule {

    /*
     * The method returns the Gson object
     * */
//    @Provides
//    @Singleton
//    Gson provideGson() {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        return gsonBuilder.create();
//    }


//    /*
//     * The method returns the Cache object
//     * */
//    @Provides
//    @Singleton
//    Cache provideCache(Application application) {
//        long cacheSize = 10 * 1024 * 1024; // 10 MB
//        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
//        return new Cache(httpCacheDirectory, cacheSize);
//    }
    /*
     * The method returns the Cache object
     * */
//    @Provides
//    @Singleton
//    API_Interceptor provideInterceptor() {
//        return new API_Interceptor.Builder().build();
//    }
//

//    /*
//     * The method returns the Okhttp object
//     * */
//    @Provides
//    @Singleton
//    OkHttpClient provideOkhttpClient(Cache cache, API_Interceptor provideInterceptor) {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.cache(cache);
//        httpClient.addInterceptor(logging);
//        httpClient.addInterceptor(provideInterceptor);
//        //httpClient.addNetworkInterceptor(new RequestInterceptor());
//        httpClient.connectTimeout(30, TimeUnit.SECONDS);
//        httpClient.readTimeout(30, TimeUnit.SECONDS);
//        return httpClient.build();
//    }


    /*
     * The method returns the Retrofit object
     * */
//    @Provides
//    @Singleton
//    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
//        return new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl("https://api.themoviedb.org/3/")
//                .client(okHttpClient)
//                .build();
//    }

    /*
     * We need the MovieApiService module.
     * For this, We need the Retrofit object, Gson, Cache and OkHttpClient .
     * So we will define the providers for these objects here in this module.
     *
     * */

    @Provides
    @Singleton
    APIRequests provideMovieApiService() {
        return APIClient.getInstance();
    }

    @Provides
    @Singleton
    Repository provideReoiitory (APIRequests apiRequests){
        return new Repository(apiRequests);
    }
}
