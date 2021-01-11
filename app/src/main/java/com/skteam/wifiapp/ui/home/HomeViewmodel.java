package com.skteam.wifiapp.ui.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.skteam.wifiapp.R;
import com.skteam.wifiapp.baseclasses.BaseViewModel;
import com.skteam.wifiapp.prefrences.SharedPre;

import java.util.List;

public class HomeViewmodel extends BaseViewModel<HomeNAv> {
    private WifiManager mainWifiObj;
    private WifiScanReceiver wifiReciever;
    private String mKnownSSID = "Redmi", mKnownPass = "sagar1998", wifis[];
    private WifiConfiguration conf;
    private int netId;
    private long timeForWifi = 10000;
    private CountDownTimer mCountDownTimer;
    private Context context;

    public HomeViewmodel(Context context, SharedPre sharedPre, Activity activity) {
        super(context, sharedPre, activity);
        this.context=context;
        mainWifiObj = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiScanReceiver();
        mainWifiObj.startScan();
    }

    public WifiScanReceiver getWifiReciever() {
        return wifiReciever;
    }

    public WifiManager getMainWifiObj() {
        return mainWifiObj;
    }

    class WifiScanReceiver extends BroadcastReceiver {
        // @SuppressLint("UseValueOf")
        public void onReceive(Context c, Intent intent) {
            List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
            wifis = new String[wifiScanList.size()];
            for (int i = 0; i < wifiScanList.size(); i++) {
                wifis[i] = ((wifiScanList.get(i)).toString());
            }
            String filtered[] = new String[wifiScanList.size()];
            int counter = 0;
            for (String eachWifi : wifis) {
                String[] temp = eachWifi.split(",");

                filtered[counter] = temp[0].substring(5).trim();//+"\n" + temp[2].substring(12).trim()+"\n" +temp[3].substring(6).trim();//0->SSID, 2->Key Management 3-> Strength

                counter++;

            }
            Log.e("wificount", String.valueOf(wifiScanList.size()));
           getNavigator().UpdateList(filtered);
        }
    }
    private void finallyConnect(String networkPass, String networkSSID) {
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", networkSSID);
        wifiConfig.preSharedKey = String.format("\"%s\"", networkPass);

        // remember id
        netId = mainWifiObj.addNetwork(wifiConfig);
        mainWifiObj.disconnect();
        mainWifiObj.enableNetwork(netId, true);
        mainWifiObj.reconnect();

        conf = new WifiConfiguration();
        conf.SSID = "\"\"" + networkSSID + "\"\"";
        conf.preSharedKey = "\"" + networkPass + "\"";
        mainWifiObj.addNetwork(conf);
        startTimer();
    }

    private void connectToWifi(final String wifiSSID) {
        if (wifiSSID.equalsIgnoreCase(mKnownSSID)) {
            finallyConnect(mKnownPass, wifiSSID);
        } else {
            Toast.makeText(context, "Hotspot Not Found.", Toast.LENGTH_SHORT).show();
        }

    }
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(timeForWifi, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Toast.makeText(context, "Time Expired.", Toast.LENGTH_SHORT).show();
                mainWifiObj.removeNetwork(netId);
                mainWifiObj.disconnect();
            }
        }.start();
    }
    public void disconnect() {
        mainWifiObj.removeNetwork(netId);
        mainWifiObj.disconnect();

    }
}
