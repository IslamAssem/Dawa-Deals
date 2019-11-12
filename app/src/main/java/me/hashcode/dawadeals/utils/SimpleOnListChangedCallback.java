package me.hashcode.dawadeals.utils;

import androidx.databinding.ObservableList;

public abstract class SimpleOnListChangedCallback extends ObservableList.OnListChangedCallback {
    @Override
    public void onChanged(ObservableList sender) {
    }

    @Override
    public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
        onChanged(sender);
    }

    @Override
    public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
        onChanged(sender);

    }

    @Override
    public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
        onChanged(sender);

    }

    @Override
    public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
        onChanged(sender);

    }
}
