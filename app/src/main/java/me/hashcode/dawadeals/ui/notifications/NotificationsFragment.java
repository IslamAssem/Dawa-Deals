package me.hashcode.dawadeals.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.adapters.NotificationsAdapter;
import me.hashcode.dawadeals.data.model.category.CategoryDetails;
import me.hashcode.dawadeals.data.model.news.Notification;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseFragment;
import me.hashcode.dawadeals.ui.mainActivity.MainActivityGoogleSample;

public class NotificationsFragment extends BaseFragment {

    private static final String TAG = "CategoriesFragment";
    @BindView(R.id.categories_recycler)
    RecyclerView notificationsRecycler;
    @Inject
    NotificationsViewModel notificationsViewModel;
    private NotificationsAdapter notificationsAdapter;
    private LinearLayoutManager layoutManager;
    private List<CategoryDetails> parentCats = new ArrayList<>();
    private List<CategoryDetails> subCats = new ArrayList<>();
    public NotificationsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        return root;
    }

    @Override
    public void initViewModel() {
        if (context instanceof BaseActivity)
            ((BaseActivity) context).observe(notificationsViewModel);
        notificationsViewModel.getNotificationsLiveData().observe(this,
                notifications -> {
                    if (notifications == null) {
                        showNotificationsError();
                    } else if (notifications.size() == 0) {
                        showNoNotifications();
                    } else showNotifications(notifications);
                });
    }

    private void showNotificationsError() {

    }

    private void showNoNotifications() {

    }

    private void showNotifications(List<Notification> notifications) {
        notificationsAdapter.add(notifications);


    }

    private void showTransactionsError() {

    }

    private void showNoTransactions() {

    }

    @Override
    public void initViews() {
        if (context instanceof MainActivityGoogleSample){
            ((MainActivityGoogleSample) context).setTextTitle("Notifications", false,true);
        }
        notificationsRecycler.setLayoutManager(layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        notificationsAdapter = new NotificationsAdapter(new OnItemClickListener<Notification>() {
            @Override
            public void OnItemClick(Notification data, View view, int position) {
                super.OnItemClick(data, view, position);
            }
        });
        notificationsRecycler.setAdapter(notificationsAdapter);
        notificationsViewModel.getNotifications();
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initData(@NonNull Bundle data) {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}