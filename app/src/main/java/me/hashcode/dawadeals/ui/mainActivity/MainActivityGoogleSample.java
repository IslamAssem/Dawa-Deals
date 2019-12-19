package me.hashcode.dawadeals.ui.mainActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.islam.custom.simpleListeners.SimpleTransitionListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.adapters.SearchAdapter;
import me.hashcode.dawadeals.data.SearchEntry;
import me.hashcode.dawadeals.database.DatabaseManager;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;
import me.hashcode.dawadeals.ui.NavigationSetup;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.utils.Utils;
import timber.log.Timber;

//
//public class MainActivityGoogleSample {
//}

/**
 * An activity that inflates a layout that has a [BottomNavigationView].
 */
public class MainActivityGoogleSample extends BaseActivity {

    @Inject
    public DatabaseManager mDatabase;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.search_edit_text)
    EditText searchEditText;
    @BindView(R.id.search_btn)
    ImageView search;
    @BindView(R.id.back)
    View back;
    @BindView(R.id.search_recycler)
    RecyclerView searchRecycler;
    @BindView(R.id.activity_root)
    MotionLayout motionLayout;
    @Inject
    SearchViewModel searchViewModel;
    SearchAdapter adapter;
    boolean lastState;
    @BindDimen(R.dimen.sp_16)
    float sp_16;
    @BindDimen(R.dimen.dp_16)
    float dp_16;
    private LiveData<NavController> currentNavController;

    @Override
    public void saveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    protected void initVaribles() {

    }

    @Override
    public void initViewModels() {
        observe(searchViewModel);
        searchViewModel.searchLiveData.observe(this, new Observer<List<SearchEntry>>() {
            @Override
            public void onChanged(List<SearchEntry> searchEntries) {
                adapter.add(searchEntries);
            }
        });
        searchViewModel.getSearchHistory();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_google_sample);
    }

    @Override
    public void initViews() {
        AndroidInjection.inject(this);
        super.initViews();
        Timber.tag("dimensions").e("dp_16 : " + dp_16);
        Timber.tag("dimensions").e("sp_16 : " + sp_16);
//        ConstraintSet.Constraint xx = motionLayout.getConstraintSet(R.id.show_search_set).getConstraint(R.id.search_edit_text);
//        xx.mCustomConstraints.get("TextSize").setFloatValue(sp_16);
        setupBottomNavigationBar();
        adapter = new SearchAdapter(new OnItemClickListener<String>() {
            @Override
            public void OnItemClick(String data, View view, int position) {
                super.OnItemClick(data, view, position);
            }
        });
        searchRecycler.setAdapter(adapter);
        searchRecycler.setLayoutManager(new LinearLayoutManager(this));
        motionLayout.setTransitionListener(new SimpleTransitionListener() {

            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
//                String i = ""+startId;
//                if (startId == R.id.show_search_set)
//                    i = "show_search_set";
//                if (startId == R.id.hide_search_set)
//                    i = "hide_search_set";
//                Timber.tag("SimpleTransitionListener").e("startId : "+i);
//                i = ""+endId;
//                if (endId == R.id.show_search_set)
//                    i = "show_search_set";
//                if (endId == R.id.hide_search_set)
//                    i = "hide_search_set";
//                Timber.tag("SimpleTransitionListener").e("endId : "+i);
            }


            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int id) {
//                String i = ""+id;
//                if (id == R.id.show_search_set)
//                    i = "show_search_set";
//                if (id == R.id.hide_search_set)
//                    i = "hide_search_set";
//                Timber.tag("SimpleTransitionListener").e("onTransitionCompleted : "+i);

            }
        });
    }

    public void hideBottom(boolean hasFocus) {
        if (hasFocus)
            findViewById(R.id.bottom_nav).setVisibility(View.GONE);

        else
            findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);

    }

    /**
     * Called on first creation and when restoring state.
     */
    private void setupBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        List<Integer> navGraphIds = new ArrayList<>();
        navGraphIds.add(R.navigation.home_navigation);
        navGraphIds.add(R.navigation.category_navigation);
        navGraphIds.add(R.navigation.trades_navigation);
        navGraphIds.add(R.navigation.wallet_navigation);
        navGraphIds.add(R.navigation.account_navigation);

        // Setup the bottom navigation view with a list of navigation graphs
        NavigationSetup navigationSetup = new NavigationSetup(
                bottomNavigationView, getSupportFragmentManager(), R.id.nav_host_container, getIntent());
        navigationSetup.navGraphIds = navGraphIds;
        navigationSetup.setupWithNavController();
        // Whenever the selected controller changes, setup the action bar.
        navigationSetup.selectedNavController.observe(this, new Observer<NavController>() {
            @Override
            public void onChanged(NavController navController) {
//                        NavDestination nv = navController.getCurrentDestination();
//                        if (nv != null) {
//                                title.setText(nv.getLabel());
//                        }
            }
        });
        currentNavController = navigationSetup.selectedNavController;
    }

    public void setTextTitle(String titlee, boolean isSearchVisible, boolean isBackVisible) {
        if (isSearchVisible) {
            search.setVisibility(View.VISIBLE);
            searchEditText.setVisibility(View.VISIBLE);
        } else {
            search.setVisibility(View.INVISIBLE);
            searchEditText.setVisibility(View.INVISIBLE);
        }
        if (isBackVisible) {
            back.setVisibility(View.VISIBLE);
        } else {
            back.setVisibility(View.GONE);
         }
        if (TextUtils.isEmpty(titlee))
            title.setVisibility(View.GONE);
        else {
            title.setVisibility(View.VISIBLE);
            title.setText(titlee);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        return currentNavController.getValue() != null && currentNavController.getValue().navigateUp();

    }

    @OnClick(R.id.search_layout)
    public void search_layout() {

    }

    @OnClick(R.id.search_btn)
    public void onClick() {
        if (lastState) {
            if (Utils.isEmpty(searchEditText))
                motionLayout.transitionToStart();
            else {
                mDatabase.searchDao().insert(new SearchEntry(searchEditText.getText().toString()));
                searchEditText.setText("");
                return;
            }
        } else {
            searchViewModel.getSearchHistory();
            motionLayout.transitionToEnd();
        }
        lastState = !lastState;
    }

    @Override
    public void onBackPressed() {
        searchEditText.setText("");
        if (lastState)
            onClick();
        else super.onBackPressed();
    }
}