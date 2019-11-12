package me.hashcode.dawadeals.ui.base;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BaseDialogFragment extends DialogFragment {

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag).addToBackStack(null);
            ft.commitAllowingStateLoss();
            manager.executePendingTransactions();
        } catch (IllegalStateException e) {
            Log.d("ABSDIALOGFRAG", "Exception", e);
        }
    }

    @Override
    public void showNow(@NonNull FragmentManager manager, @Nullable String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag).addToBackStack(null);
            ft.commitNowAllowingStateLoss();
            manager.executePendingTransactions();
        } catch (IllegalStateException e) {
            Log.d("ABSDIALOGFRAG", "Exception", e);
        }    }

    boolean mIsStateAlreadySaved = false;
    boolean mPendingShowDialog = false;

    @Override
    public void onResume() {
        onResumeFragments();
        super.onResume();
    }

    public void onResumeFragments(){
        mIsStateAlreadySaved = false;
        if(mPendingShowDialog){
            mPendingShowDialog = false;
            showSnoozeDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsStateAlreadySaved = true;
    }

    private void showSnoozeDialog() {
        if(mIsStateAlreadySaved){
            mPendingShowDialog = true;
        }else{
            FragmentManager fm = getFragmentManager();
            BaseDialogFragment snoozeDialog = new BaseDialogFragment();
            snoozeDialog.show(fm, "BaseDialogFragment");
        }
    }
}