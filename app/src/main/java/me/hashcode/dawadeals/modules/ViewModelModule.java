package me.hashcode.dawadeals.modules;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
 import me.hashcode.dawadeals.factory.ViewModelFactory;
import me.hashcode.dawadeals.ui.categories.CategoriesViewModel;
import me.hashcode.dawadeals.ui.home.HomeViewModel;
import me.hashcode.dawadeals.ui.login.LoginViewModel;
import me.hashcode.dawadeals.ui.splash.SplashViewModel;
import me.hashcode.dawadeals.ui.trades.TradesViewModel;
import me.hashcode.dawadeals.ui.wallet.WalletViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    protected abstract ViewModel splashViewModel(SplashViewModel splashViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    protected abstract ViewModel homeViewModel(HomeViewModel splashViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel.class)
    protected abstract ViewModel categoriesViewModel(CategoriesViewModel categoriesViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel.class)
    protected abstract ViewModel walletViewModel(WalletViewModel categoriesViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(TradesViewModel.class)
    protected abstract ViewModel tradesViewModel(TradesViewModel tradesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    protected abstract ViewModel loginViewModel(LoginViewModel tradesViewModel);
}