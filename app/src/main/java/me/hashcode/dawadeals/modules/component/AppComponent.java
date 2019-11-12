package me.hashcode.dawadeals.modules.component;


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import me.hashcode.dawadeals.App;
import me.hashcode.dawadeals.modules.UiModule;
import me.hashcode.dawadeals.modules.ApiModule;
import me.hashcode.dawadeals.modules.ViewModelModule;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseFragment;


/*
 * We mark this interface with the @Component annotation.
 * And we define all the modules that can be injected.
 * Note that we provide AndroidSupportInjectionModule.class
 * here. This class was not created by us.
 * It is an internal class in Dagger 2.10.
 * Provides our activities and fragments with given module.
 * */
@Component(modules = {
        ApiModule.class,
//        DbModule.class,
          ViewModelModule.class,
        UiModule.class,
         AndroidSupportInjectionModule.class})
@Singleton
public interface AppComponent {


    /* We will call this builder interface from our custom Application class.
     * This will set our application object to the AppComponent.
     * So inside the AppComponent the application instance is available.
     * So this application instance can be accessed by our modules
     * such as ApiModule when needed
     * */
    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }


    /*
     * This is our custom Application class
     * */
    void inject(App appController);
    void inject(BaseActivity activity);
    void inject(BaseFragment fragment);
}