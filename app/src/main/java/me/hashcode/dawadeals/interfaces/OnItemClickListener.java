package me.hashcode.dawadeals.interfaces;

import android.view.View;

import androidx.annotation.CallSuper;

public abstract class OnItemClickListener<T> {
    protected void OnItemClick() {
    }

    protected void OnItemClick(T data) {
    }

    protected void OnItemClick(int position) {
    }

    protected void OnItemClick(T data, int position) {
    }

    protected void OnItemClick(View view, int position) {
    }
    @CallSuper
    public void OnItemClick(T data, View view, int position) {
          OnItemClick();
          OnItemClick(data);
          OnItemClick(position) ;
          OnItemClick(data, position) ;
          OnItemClick(view, position) ;
    }

}