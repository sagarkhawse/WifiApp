package com.skteam.wifiapp.brodcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

public class WifiStateReceiver extends BroadcastReceiver {
    public static WifiListener listener;


    public WifiStateReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
        switch (wifiStateExtra) {
            case WifiManager.WIFI_STATE_ENABLED:
                if(listener!=null) {
                    listener.onWifiConnected(true);
                }
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                if(listener!=null) {
                    listener.onWifiConnected(false);
                }
                break;
        }
    }
    public interface WifiListener {
        void onWifiConnected(boolean isConnected);
    }
}
