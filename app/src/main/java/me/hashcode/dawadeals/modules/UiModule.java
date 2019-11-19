package me.hashcode.dawadeals.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.hashcode.dawadeals.ui.account.AccountFragment;
import me.hashcode.dawadeals.ui.categories.CategoriesFragment;
import me.hashcode.dawadeals.ui.home.HomeFragment;
import me.hashcode.dawadeals.ui.login.LoginFragment;
import me.hashcode.dawadeals.ui.mainActivity.MainActivity;
import me.hashcode.dawadeals.ui.mainActivity.MainActivityGoogleSample;
import me.hashcode.dawadeals.ui.notifications.NotificationsFragment;
import me.hashcode.dawadeals.ui.register.RegisterFragment;
import me.hashcode.dawadeals.ui.splash.Splash;
import me.hashcode.dawadeals.ui.trade_details.AddNewTradeFragment;
import me.hashcode.dawadeals.ui.trade_details.TradeDetailsFragment;
import me.hashcode.dawadeals.ui.trade_details.TradeDetailsPage;
import me.hashcode.dawadeals.ui.trade_details.TradesBuyFragment;
import me.hashcode.dawadeals.ui.trade_details.TradesSellFragment;
import me.hashcode.dawadeals.ui.trades.TradesFragment;
import me.hashcode.dawadeals.ui.wallet.WalletFragment;
import me.hashcode.dawadeals.ui.wallet.WalletTransactionFragment;

@Module
public abstract class UiModule {

    //activity
    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector()
    abstract MainActivityGoogleSample contributeMainActivityGoogle();
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
    @ContributesAndroidInjector()
    abstract LoginFragment contributeLoginFragment ();

    @ContributesAndroidInjector()
    abstract RegisterFragment contributeRegisterFragment();

    @ContributesAndroidInjector()
    abstract TradeDetailsFragment contributeTradeDetails();

    @ContributesAndroidInjector()
    abstract TradeDetailsPage contributeTradeDetailss();

    @ContributesAndroidInjector()
    abstract TradesBuyFragment contributeTradeBuyDetailss();

    @ContributesAndroidInjector()
    abstract TradesSellFragment contributeTradeSellDetailss();

    @ContributesAndroidInjector()
    abstract AddNewTradeFragment contributeAddNewTrade();

    @ContributesAndroidInjector()
    abstract AccountFragment contributeAccountFragment();

    @ContributesAndroidInjector()
    abstract NotificationsFragment contributeNotificationsFragment();

    @ContributesAndroidInjector()
    abstract WalletTransactionFragment contributeWalletTransactionFragment();
}