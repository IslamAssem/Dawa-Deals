package me.hashcode.dawadeals.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.hashcode.dawadeals.ui.categories.CategoriesFragment;
import me.hashcode.dawadeals.ui.home.HomeFragment;
import me.hashcode.dawadeals.ui.mainActivity.MainActivity;
import me.hashcode.dawadeals.ui.splash.Splash;
import me.hashcode.dawadeals.ui.trades.TradesFragment;
import me.hashcode.dawadeals.ui.trades.TradesViewModel;
import me.hashcode.dawadeals.ui.wallet.WalletFragment;

@Module
public abstract class UiModule {

    //activity
    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();
    @ContributesAndroidInjector()
    abstract Splash contributeSplash ();



    //fragments
    @ContributesAndroidInjector()
    abstract HomeFragment contributeHome ();
    @ContributesAndroidInjector()
    abstract CategoriesFragment contributeCategory ();
    @ContributesAndroidInjector()
    abstract WalletFragment contributeWalletFragment ();
    @ContributesAndroidInjector()
    abstract TradesFragment contributeTradesFragment ();
}