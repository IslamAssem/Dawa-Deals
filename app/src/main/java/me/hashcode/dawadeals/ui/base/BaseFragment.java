package me.hashcode.dawadeals.ui.base;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import dagger.android.HasAndroidInjector;
import me.hashcode.dawadeals.App;
import me.hashcode.dawadeals.interfaces.HasTag;
import me.hashcode.dawadeals.utils.Utils;

public abstract class BaseFragment extends Fragment implements HasTag {
     public Context context;
     private View view;
    public boolean isRunning;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view = super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HasAndroidInjector androidInjector = App.getInstance();
        androidInjector.androidInjector().inject(this);
        ButterKnife.bind(this, view);
        view.setClickable(true);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        initViewModel();
        Bundle bundle = Utils.firstNonNull(savedInstanceState, getArguments());
        if (bundle != null) {
            initData(bundle);
        }
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);
        View view = getView();
        if (view == null)
            view = this.view;
        int preLayerType = view.getLayerType();
        if (animation == null && nextAnim != 0)
            animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
        if (animation != null) {
            View finalView = view;
            finalView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finalView.setLayerType(preLayerType, null);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        return animation;
    }

    public abstract void initViews();

    public abstract void initVariables();
    public abstract void initData(@NonNull Bundle data);
    public abstract void initViewModel();
}
