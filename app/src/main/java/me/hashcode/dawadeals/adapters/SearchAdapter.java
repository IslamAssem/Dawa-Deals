package me.hashcode.dawadeals.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.SearchEntry;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;
import me.hashcode.dawadeals.utils.Utils;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    List<SearchEntry> searchValues;

    OnItemClickListener<String> onItemClickListener;

    public SearchAdapter(OnItemClickListener<String> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        searchValues = new ArrayList<>();
    }

    public void add(List<SearchEntry> searchEntries) {
        if (Utils.isEmpty(searchEntries))
            return;
        int count = getItemCount();
        for (int i = 0; i < searchEntries.size(); i++)
            if (searchValues.contains(searchEntries.get(i)))
                searchEntries.remove(i--);
        if (Utils.isEmpty(searchEntries))
            return;
        searchValues.addAll(searchEntries);
        notifyItemRangeInserted(count, searchEntries.size());
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.searchView.setText(searchValues.get(position).getSearch_text());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.OnItemClick(searchValues.get(holder.getAdapterPosition()).getSearch_text(), holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchValues.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView searchView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            searchView = itemView.findViewById(R.id.search_text);
        }
    }
}
