package me.hashcode.dawadeals.ui.mainActivity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import butterknife.BindView;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.ui.base.BaseActivity;
public class MainActivity extends BaseActivity {
    @Override
    public void saveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_wallet, R.id.navigation_account)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void initVaribles() {
    }
    @Override
    public void initViewModels() {
    }
    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    public void hideBottom(boolean hasFocus) {
        if (hasFocus)
            navView.setVisibility(View.GONE);
        else navView.setVisibility(View.VISIBLE);

    }
}