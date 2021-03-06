package com.islam.slider.Transformers;

import android.view.View;

import androidx.core.view.ViewCompat;

public class ZoomOutTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        final float scale = 1f + Math.abs(position);
        ViewCompat.setScaleX(view,scale);
        ViewCompat.setScaleY(view,scale);
        ViewCompat.setPivotX(view,view.getWidth() * 0.5f);
        ViewCompat.setPivotY(view,view.getWidth() * 0.5f);
        ViewCompat.setAlpha(view,position < -1f || position > 1f ? 0f : 1f - (scale - 1f));
        if(position < -0.9){
            //-0.9 to prevent a small bug
            ViewCompat.setTranslationX(view,view.getWidth() * position);
        }
    }

}