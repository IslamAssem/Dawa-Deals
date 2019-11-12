package me.hashcode.dawadeals.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.model.ads.Ad;
import me.hashcode.dawadeals.databinding.RecyclerHomeAdBinding;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {

    List<Ad> ads;
    OnItemClickListener onAdClick;

    public AdsAdapter(OnItemClickListener onAdClick) {
        this.onAdClick = onAdClick;
        ads = new ArrayList<>();
    }

    public AdsAdapter(List<Ad> ads, OnItemClickListener onAdClick) {
        this.ads = ads;
        this.onAdClick = onAdClick;
    }
    public void add(Ad...ads){
        int start = getItemCount();
        this.ads.addAll(Arrays.asList((ads)));
        notifyItemRangeInserted(start,ads.length);
    }
    public void add(List<Ad> ads){
        int start = getItemCount();
        this.ads.addAll(ads);
        notifyItemRangeInserted(start,ads.size());
    }

    @NonNull
    @Override
    public AdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerHomeAdBinding adBinding = RecyclerHomeAdBinding.
                inflate(inflater,parent,false);
        return new AdsViewHolder(adBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {
        holder.bind(ads.get(position));

    }

    @Override
    public int getItemCount() {
        return ads == null?0:ads.size();
    }

    public static class AdsViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerHomeAdBinding binding;

        public AdsViewHolder(RecyclerHomeAdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Ad item) {
            binding.setAd(item);
            binding.executePendingBindings();
        }
    }
}
