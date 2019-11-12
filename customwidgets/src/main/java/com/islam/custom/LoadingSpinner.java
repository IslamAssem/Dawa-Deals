package com.islam.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class LoadingSpinner extends AppCompatImageView {

    private static final int ROTATE_ANIMATION_DURATION = 800;

    public LoadingSpinner(Context context) {
        super(context, null);
    }

    public LoadingSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingSpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Set and scale the image
        setImageResource(R.drawable.ic_ball);

        // Start the animation
        startAnimation();
    }

    /**
     * Starts the rotate animation.
     */
    private void startAnimation() {
        clearAnimation();

        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(ROTATE_ANIMATION_DURATION);
        rotate.setRepeatCount(Animation.INFINITE);
        startAnimation(rotate);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        if (visibility == View.VISIBLE) {
            startAnimation();
        } else {
            clearAnimation();
        }
    }
}