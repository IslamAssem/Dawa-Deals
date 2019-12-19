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
import me.hashcode.dawadeals.data.model.news.Notification;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;
import me.hashcode.dawadeals.utils.Utils;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {
    List<Notification> notifications;

    OnItemClickListener<Notification> onItemClickListener;

    public NotificationsAdapter(OnItemClickListener<Notification> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        notifications = new ArrayList<>();
    }

    public void add(List<Notification> notifications) {
        if (Utils.isEmpty(notifications))
            return;
        int count = getItemCount();
        for (int i = 0; i < notifications.size(); i++)
            if (this.notifications.contains(notifications.get(i)))
                notifications.remove(i--);
        if (Utils.isEmpty(notifications))
            return;
        this.notifications.addAll(notifications);
        notifyItemRangeInserted(count, notifications.size());
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.notificationText.setText(notifications.get(position).getDescription());
        holder.date.setText(notifications.get(position).getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.OnItemClick(notifications.get(holder.getAdapterPosition()), holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView notificationText;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            notificationText = itemView.findViewById(R.id.notification_text);
        }
    }
}
