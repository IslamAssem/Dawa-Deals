package me.hashcode.dawadeals.interfaces;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public interface LifecycleObserver extends androidx.lifecycle.LifecycleObserver {
    //private static LifecycleObserver instance;

    //    public static LifecycleObserver set(LifecycleObserver instance){
//        if(LifecycleObserver.instance==null)
//            LifecycleObserver.instance=instance;
//        return LifecycleObserver.instance;
//    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public  void onPause();
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public  void onDestroy();
}
