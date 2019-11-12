package com.islam.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

public class CustomEditText extends AppCompatEditText {

    private OnCloseSoftKeyboardListener mBackButtonListener;
    private OnTouchListener mTouchListener;

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    // HANDLING OF SOFT KEYBOARD BACK BUTTON
    public void setOnCloseSoftKeyboardListener(OnCloseSoftKeyboardListener callback) {
        mBackButtonListener = callback;
    }


    /**
     * Overrides the handling of the back key to move fields or whatever, instead of dismissing the input method.
     */
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (mBackButtonListener != null && event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
            return mBackButtonListener.onCloseSoftKeyboard(this);
        return super.dispatchKeyEventPreIme(event);
    }


    public interface OnCloseSoftKeyboardListener {

        /**
         * @return true if event was consumed, false otherwise
         */
        boolean onCloseSoftKeyboard(EditText view);
    }
}
