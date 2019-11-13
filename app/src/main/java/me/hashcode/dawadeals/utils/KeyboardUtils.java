package me.hashcode.dawadeals.utils;

import android.content.Intent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.interfaces.LifecycleObserver;

public abstract class KeyboardUtils implements LifecycleObserver{
    private AppCompatActivity activity;
    private int defaulThreeshold = -1;
    private boolean keyboardListenersAttached = false;
    private ViewGroup rootLayout;
    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (defaulThreeshold == -1)
            defaulThreeshold = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
            int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
            int contentViewTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(activity);

            if(heightDiff <= defaulThreeshold + contentViewTop){
                onHideKeyboard();

                Intent intent = new Intent("KeyboardWillHide");
                broadcastManager.sendBroadcast(intent);
            } else {
                int keyboardHeight = heightDiff - contentViewTop;
                onShowKeyboard(keyboardHeight);

                Intent intent = new Intent("KeyboardWillShow");
                intent.putExtra("KeyboardHeight", keyboardHeight);
                broadcastManager.sendBroadcast(intent);
            }
        }
    };
    public KeyboardUtils(AppCompatActivity activity) {
        this.activity = activity;
        activity.getLifecycle().addObserver(this);
    }
    public abstract void onShowKeyboard(int keyboardHeight);
    public abstract void onHideKeyboard();

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        rootLayout = activity.findViewById(R.id.activity_root);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
        keyboardListenersAttached = true;
    }
    @Override
    public void onResume() {
        attachKeyboardListeners();
    }

    @Override
    public void onPause() {
        try {
            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(keyboardLayoutListener);
            keyboardListenersAttached = false;
        } catch (Exception e) {
        }

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        if (keyboardListenersAttached) {
            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }
            activity.getLifecycle().removeObserver(this);

    }
}
