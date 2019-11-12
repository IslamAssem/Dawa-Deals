package me.hashcode.dawadeals.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.islam.custom.stickyHeader.StickHeaderItemDecoration;
import com.islam.custom.stickyHeader.StickHeaderRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.model.trade.HeaderDataImpl;
import me.hashcode.dawadeals.data.model.trade.Transaction;
import me.hashcode.dawadeals.databinding.RecyclerHomeLatestTransitionBinding;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;

public class TransactionsAdapter extends StickHeaderRecyclerView<Transaction, HeaderDataImpl> {

    List<Transaction> transactions;
    OnItemClickListener ontranactionClick;

    public TransactionsAdapter(OnItemClickListener ontranactionClick) {
        this.ontranactionClick = ontranactionClick;
        transactions = new ArrayList<>();
        setHeaderAndData(transactions,new HeaderDataImpl(HeaderDataImpl.HEADER_TYPE_1,R.layout.recycler_home_latest_transition_header));
    }

    public TransactionsAdapter(List<Transaction> transactions, OnItemClickListener ontranactionClick) {
        this.transactions = transactions;
        this.ontranactionClick = ontranactionClick;
        setHeaderAndData(transactions,new HeaderDataImpl(HeaderDataImpl.HEADER_TYPE_1,R.layout.recycler_home_latest_transition_header));
    }
    public void add(Transaction... transactions){
        int start = getItemCount();
        this.transactions.addAll(Arrays.asList((transactions)));
        notifyItemRangeInserted(start, transactions.length);
    }
    public void add(List<Transaction> transactions){
        int start = getItemCount();
        this.transactions.addAll(transactions);
        notifyItemRangeInserted(start, transactions.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HeaderDataImpl.HEADER_TYPE_1)
        return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_home_latest_transition_header, parent, false));

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerHomeLatestTransitionBinding tranactionsBinding = RecyclerHomeLatestTransitionBinding.
                inflate(inflater, parent, false);
        return new TranactionsViewHolder(tranactionsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TranactionsViewHolder)
            ((TranactionsViewHolder)holder).bind(transactions.get(position));

    }

    @Override
    public int getItemCount() {
        return transactions == null?0: transactions.size();
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {

    }

    public static class TranactionsViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerHomeLatestTransitionBinding binding;

        public TranactionsViewHolder(RecyclerHomeLatestTransitionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Transaction item) {
            binding.setTranaction(item);
            binding.executePendingBindings();
        }
    }
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeader;

        HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
