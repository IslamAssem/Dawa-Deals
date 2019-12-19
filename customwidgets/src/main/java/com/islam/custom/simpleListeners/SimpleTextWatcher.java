package com.islam.custom.simpleListeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class SimpleTextWatcher implements TextWatcher {
    public EditText editText;

    public SimpleTextWatcher(EditText editText) {
        this.editText = editText;
    }

    public SimpleTextWatcher() {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
