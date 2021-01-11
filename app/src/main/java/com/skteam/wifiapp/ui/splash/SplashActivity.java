package com.skteam.wifiapp.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.skteam.wifiapp.R;
import com.skteam.wifiapp.baseclasses.BaseActivity;
import com.skteam.wifiapp.databinding.ActivityMainBinding;
import com.skteam.wifiapp.setting.CommonUtils;
import com.skteam.wifiapp.ui.home.HomeActivity;

public class SplashActivity extends BaseActivity<ActivityMainBinding, SpalshViewmodel> implements SplashNav {
    private SpalshViewmodel spalshViewmodel;
    private ActivityMainBinding binding;
    private Handler handler;
    private Runnable runnable;

    @Override
    public int getBindingVariable() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public SpalshViewmodel getViewModel() {
        return spalshViewmodel = new SpalshViewmodel(this, getSharedPre(), this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        };
        getCommonUtils().setAnimationBounse(binding.Logo);
        handler.postDelayed(runnable, 1000);

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
        } else {
            Toast.makeText(this, "Please Start your WIFI First", Toast.LENGTH_SHORT).show();
        }

    }
}