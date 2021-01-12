package com.skteam.wifiapp.ui.register;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.skteam.wifiapp.R;
import com.skteam.wifiapp.baseclasses.BaseActivity;
import com.skteam.wifiapp.databinding.ActivityRegisterHotspotBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.disposables.Disposable;

public class RegisterHotspot extends BaseActivity<ActivityRegisterHotspotBinding, RegisterViewModel> implements RegisterNav {
    private WifiManager wifimanager;
    private WifiConfiguration config;
    private RegisterViewModel viewModel;
    private ActivityRegisterHotspotBinding binding;
    private Disposable disposable;

    @Override
    public int getBindingVariable() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_hotspot;
    }

    @Override
    public RegisterViewModel getViewModel() {
        return viewModel = new RegisterViewModel(this, getSharedPre(), this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        wifimanager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        GetSSID();
        binding.backNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHotspotName();
                wifimanager.disconnect();
                finish();
            }
        });
        binding.registerYourDeviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHotspotName();
                getSharedPre().setHotspotName(binding.ssidTxt.getText().toString());
               binding.backNow.performClick();

            }
        });

    }

    private void GetSSID() {

        Method[] methods = wifimanager.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().equals("getWifiApConfiguration")) {
                try {
                    config = (WifiConfiguration) m.invoke(wifimanager);
                    binding.ssidTxt.setText(config.SSID);
                    binding.password.setText(config.preSharedKey);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                // here, the "config" variable holds the info, your SSID is in
                // config.SSID
            }
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    @Override
    public void onWifiConnected(boolean isConnected) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void turnOnHotspot() {

                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        wifimanager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {

                            @Override
                            public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                                super.onStarted(reservation);
                                Log.d("HOTSPOT", "Wifi Hotspot is on now");
                            }

                            @Override
                            public void onStopped() {
                                super.onStopped();
                                Log.d("HOTSPOT", "onStopped: ");
                            }

                            @Override
                            public void onFailed(int reason) {
                                super.onFailed(reason);
                                Log.d("HOTSPOT", "onFailed: ");
                            }
                        }, new Handler());
                    }
    public  boolean setHotspotName() {
        try {
            Method getConfigMethod = wifimanager.getClass().getMethod("getWifiApConfiguration");
            WifiConfiguration wifiConfig = (WifiConfiguration) getConfigMethod.invoke(wifimanager);

            wifiConfig.SSID = binding.ssidTxt.getText().toString();
            wifiConfig.preSharedKey = binding.password.getText().toString();

            Method setConfigMethod = wifimanager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class);
            setConfigMethod.invoke(wifimanager, wifiConfig);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}