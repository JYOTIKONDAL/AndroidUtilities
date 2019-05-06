package com.phappy.library;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.RequiresPermission;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

/**
 * Created by singhParamveer on 2/11/2017.
 * Utility class for network related queries
 */

public class NetworkUtil {

    public static int TYPE_NOT_CONNECTED = 0;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_IS_CONNECTING = 3;
    private NetworksBroadcast networksBroadcast;
    private NetworkStateListener networkStateListener;

    /**
     * Get current status of network connection.
     *
     * @return status of current connection in int:
     * TYPE_NOT_CONNECTED = 0
     * TYPE_WIFI = 1
     * TYPE_MOBILE = 2
     * TYPE_IS_CONNECTING = 3
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;

            NetworkInfo[] info = cm.getAllNetworkInfo();

            for (int i = 0; i < info.length; i++) {
                if (info[i].getDetailedState() == NetworkInfo.DetailedState.CONNECTING) {
                    return TYPE_IS_CONNECTING;
                }
            }
        }

        return TYPE_NOT_CONNECTED;
    }

    /**
     * Checks whether connected/connecting to any available network
     *
     * @param context
     * @return true: if connected or connecting
     * false: otherwise
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null
                && activeNetworkInfo.isConnectedOrConnecting();
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    private String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        } else if (conn == NetworkUtil.TYPE_IS_CONNECTING) {
            status = "Poor internet connection";
        }
        return status;
    }

    /**
     * Use this method to register for changes in network connectivity
     *
     * @param activity             Activity's context
     * @param networkStateListener the listener to receive network callbacks
     */
    public void initializeNetworkBroadcast(Activity activity, NetworkStateListener networkStateListener) {
        this.networkStateListener = networkStateListener;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        if (networksBroadcast == null)
            networksBroadcast = new NetworksBroadcast();
        activity.registerReceiver(networksBroadcast, intentFilter);
    }

    /**
     * Unregister from any network's broadcasts. Use proper activity lifecycle methods to register/unregister from any broadcasts
     *
     * @param activity Activity's context
     */
    public void unregisterNetworkBroadcast(Activity activity) {
        try {
            if (networksBroadcast != null) {
                activity.unregisterReceiver(networksBroadcast);
            }
        } catch (IllegalArgumentException e) {
            networksBroadcast = null;
        }
    }

    public void showNoConnectivityToast(Activity activity) {
        Toast.makeText(activity.getApplicationContext(),"No internet available", Toast.LENGTH_SHORT).show();
    }

    public class NetworksBroadcast extends BroadcastReceiver {

        @Override
        @RequiresPermission(ACCESS_NETWORK_STATE)
        public void onReceive(Context context, Intent intent) {
            String status = getConnectivityStatusString(context);
            if (networkStateListener != null) {
                networkStateListener.onNetworkStatus(status);
            }
        }
    }

    public interface NetworkStateListener {
        void onNetworkStatus(String status);
    }
}
