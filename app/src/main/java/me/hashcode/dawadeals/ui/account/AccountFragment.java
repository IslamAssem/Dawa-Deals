package me.hashcode.dawadeals.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import butterknife.OnClick;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.model.user.UserDetails;
import me.hashcode.dawadeals.ui.base.BaseFragment;
import me.hashcode.dawadeals.ui.mainActivity.MainActivityGoogleSample;
import me.hashcode.dawadeals.utils.Utils;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class AccountFragment extends BaseFragment {

    private static final String TAG = "accountfragment";
    private AccountViewModel accountViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void initViews() {

        if (context instanceof MainActivityGoogleSample)
            ((MainActivityGoogleSample) context).setTextTitle(Utils.getStringRes(R.string.account_settings), false, false);
        if (UserDetails.readUser() == null)
            findNavController(this).navigate(R.id.action_login);
//        else if (getActivity() != null)getActivity().onBackPressed();
    }

    @OnClick(R.id.notifications)
    public void notifications() {
        findNavController(this).navigate(R.id.action_notification);
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initData(@NonNull Bundle data) {

    }

    @Override
    public void initViewModel() {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);


    }

    @Override
    public String getTAG() {
        return TAG;
    }
}