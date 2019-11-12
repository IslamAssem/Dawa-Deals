package me.hashcode.dawadeals;
/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;

import androidx.multidex.MultiDexApplication;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.offline.ActionFileUpgradeUtil;
import com.google.android.exoplayer2.offline.DefaultDownloadIndex;
import com.google.android.exoplayer2.offline.DefaultDownloaderFactory;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Log;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import me.hashcode.dawadeals.modules.component.AppComponent;
import me.hashcode.dawadeals.modules.component.DaggerAppComponent;
import me.hashcode.dawadeals.recievers.ConnectivityReceiver;
import me.hashcode.dawadeals.utils.Constants;
import me.hashcode.dawadeals.utils.LocaleHelper;
import timber.log.Timber;

public class App extends MultiDexApplication implements HasAndroidInjector {
    public  static final String TAG="basketball federation";
    static App mApp;
    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

     public DispatchingAndroidInjector<Object> activityInjector() {
        return dispatchingAndroidInjector;
    }


    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
    public App() {
        mApp=this;
    }

    public static synchronized App getInstance() {
        return mApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(
                    ConnectivityReceiver.getInstance(),
                    new IntentFilter(Constants.InternetChangeFilter));
        }
        AppComponent mComponent = DaggerAppComponent.builder()
                .application(this)
                .build();

        mComponent.inject(this);
//        String mode= Utils.getSharedPrefrences("mode","0");
//        try{
//            int m=Integer.parseInt(mode);
//            if(m==0)
//                BaseActivity.homeClass= HomeBasic.class;
//            else
//                BaseActivity.homeClass= Home.class;
//
//
//        }catch (Exception e){
//
//        }
        //CartDB.setInstance(this);
        //
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (ConnectivityReceiver.getInstance() != null)
                    unregisterReceiver(ConnectivityReceiver.getInstance());
            }
        }
        catch (Exception ignored) {
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext((LocaleHelper.onAttach(base
                )));
        //MultiDex.install(this);

    }
}
