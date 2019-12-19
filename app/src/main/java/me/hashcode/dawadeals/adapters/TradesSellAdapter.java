package me.hashcode.dawadeals.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.islam.custom.stickyHeader.StickHeaderRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.model.trade.HeaderDataImpl;
import me.hashcode.dawadeals.data.model.trade.Transaction;
import me.hashcode.dawadeals.databinding.RecyclerTradeBuySellBinding;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;
import me.hashcode.dawadeals.utils.Utils;

public class TradesSellAdapter extends StickHeaderRecyclerView<Transaction, HeaderDataImpl> {

    List<Transaction> transactions;
    OnItemClickListener ontranactionClick;

    public TradesSellAdapter(OnItemClickListener ontranactionClick) {
        this.ontranactionClick = ontranactionClick;
        transactions = new ArrayList<>();
        setHeaderAndData(transactions,new HeaderDataImpl(HeaderDataImpl.HEADER_TYPE_1,R.layout.recycler_trade_buy_sell_header));
    }
    public void add(Transaction... transactions){
        this.add(Arrays.asList((transactions)));
    }
    public void add(List<Transaction> transactions){
        Utils.removeRedundant(this.transactions,transactions);
        if (Utils.isEmpty(transactions))
            return;
        int start = getItemCount();
        this.transactions.addAll(transactions);
        setHeaderAndData(transactions);
        notifyItemRangeInserted(start, transactions.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HeaderDataImpl.HEADER_TYPE_1)
        return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_trade_buy_sell_header, parent, false));

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerTradeBuySellBinding tranactionsBinding = RecyclerTradeBuySellBinding.
                inflate(inflater, parent, false);
        return new TradeViewHolder(tranactionsBinding);
    }

    public void setOntranactionClick(OnItemClickListener ontranactionClick) {
        this.ontranactionClick = ontranactionClick;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TradeViewHolder)
            ((TradeViewHolder) holder).bind(transactions.get(position), ontranactionClick);

    }

    @Override
    public int getItemCount() {
        return transactions == null?0: transactions.size();
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {

    }

    public static class TradeViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerTradeBuySellBinding binding;

        public TradeViewHolder(RecyclerTradeBuySellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Transaction item, OnItemClickListener ontranactionClick) {
            binding.setTranaction(item);
            binding.executePendingBindings();
            if (ontranactionClick != null)
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ontranactionClick.OnItemClick(item, v, getAdapterPosition());
                    }
                });
        }
    }
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeader;

        HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
