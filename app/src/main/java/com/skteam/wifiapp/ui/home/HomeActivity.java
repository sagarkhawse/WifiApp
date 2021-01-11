package com.skteam.wifiapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.skteam.wifiapp.R;
import com.skteam.wifiapp.baseclasses.BaseActivity;
import com.skteam.wifiapp.databinding.ActivityHomeBinding;

import java.util.List;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewmodel> implements HomeNAv {
    private ActivityHomeBinding binding;
    private HomeViewmodel viewmodel;
    private WifiManager wifiManager;

    @Override
    public int getBindingVariable() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeViewmodel getViewModel() {
        return viewmodel = new HomeViewmodel(this, getSharedPre(), this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewmodel.setNavigator(this);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        switch (wifiManager.getWifiState()) {
            case WifiManager.WIFI_STATE_ENABLED:
                binding.wifiSwitch.setChecked(true);
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                binding.wifiSwitch.setChecked(false);
                break;
        }
        binding.wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                } else {
                    wifiManager.setWifiEnabled(false);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }
        registerReceiver(viewmodel.getWifiReciever(), new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    @Override
    protected void onPause() {
        unregisterReceiver(viewmodel.getWifiReciever());
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        viewmodel.disconnect();
        super.onDestroy();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            getInternetDialog().dismiss();
        } else {
            getInternetDialog().show();
        }
    }

    @Override
    public void onWifiConnected(boolean isConnected) {
        if (isConnected) {
            Toast.makeText(this, "Wifi Started", Toast.LENGTH_SHORT).show();
            binding.wifiSwitch.setChecked(true);
        } else {
            Toast.makeText(this, "Please Start your WIFI First", Toast.LENGTH_SHORT).show();
            binding.wifiSwitch.setChecked(false);
        }

    }

    @Override
    public void UpdateList(String[] filtered) {
        if (filtered != null) {
            Toast.makeText(this, filtered[0], Toast.LENGTH_SHORT).show();
        }
    }
}