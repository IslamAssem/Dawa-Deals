package me.hashcode.dawadeals.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.hashcode.dawadeals.App;
import me.hashcode.dawadeals.interfaces.OnConnectionStatusChanged;

/**
 * Created by mohamed on 2/18/18.
 */

public class ConnectivityReceiver extends BroadcastReceiver {
    private static ConnectivityReceiver receiver;
    private ArrayList<OnConnectionStatusChanged> mOnConnectionStatusChanged;

    public static BroadcastReceiver getInstance() {
        if (receiver == null)
            receiver = new ConnectivityReceiver();
        if (receiver.mOnConnectionStatusChanged == null)
            receiver.mOnConnectionStatusChanged = new ArrayList<>();
        return receiver;
    }
    public static boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) App.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public static void observeNetworkStatus(OnConnectionStatusChanged listener) {
        if (listener != null)
            ((ConnectivityReceiver) getInstance()).mOnConnectionStatusChanged.add(listener);

    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        notifyListeners(activeNetwork != null
                && activeNetwork.isConnectedOrConnecting());
    }

    public void notifyListeners(boolean status) {
        for (OnConnectionStatusChanged listener : ((ConnectivityReceiver) getInstance()).mOnConnectionStatusChanged)
            if (listener != null)
                listener.OnConnectionStatusChanged(status);
    }
}