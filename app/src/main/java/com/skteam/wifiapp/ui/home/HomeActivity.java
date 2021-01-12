package com.skteam.wifiapp.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.skteam.wifiapp.R;
import com.skteam.wifiapp.baseclasses.BaseActivity;
import com.skteam.wifiapp.databinding.ActivityHomeBinding;
import com.skteam.wifiapp.ui.home.adapter.PlanAdapter;
import com.skteam.wifiapp.ui.home.adapter.WifiNearbyAdapter;
import com.skteam.wifiapp.ui.home.model.Plan;
import com.skteam.wifiapp.ui.register.RegisterHotspot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewmodel> implements HomeNAv {
    private ActivityHomeBinding binding;
    private HomeViewmodel viewmodel;
    private WifiManager wifiManager;
    private WifiNearbyAdapter adapter;
    private PlanAdapter planAdapter;
    private List<Plan> getPlanList = new ArrayList<>();

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
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        binding = getViewDataBinding();
        viewmodel.setNavigator(this);
        Plan plan = new Plan("100", "1", "UNLIMETED");
        Plan plan2 = new Plan("120", "1.5", "UNLIMETED");
        Plan plan3 = new Plan("200", "3", "UNLIMETED");
        getPlanList.add(plan);
        getPlanList.add(plan2);
        getPlanList.add(plan3);
        adapter = new WifiNearbyAdapter(this, wifiManager);
        planAdapter = new PlanAdapter(this);
        planAdapter.UpdateList(getPlanList);
        binding.planRecycler.setAdapter(planAdapter);

        binding.wifiRecycler.setAdapter(adapter);
        binding.seeAllRouterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.wifiRecycler.getVisibility() == View.VISIBLE) {
                    binding.wifiRecycler.setVisibility(View.GONE);
                } else {
                    binding.wifiRecycler.setVisibility(View.VISIBLE);
                }
            }
        });
        switch (wifiManager.getWifiState()) {
            case WifiManager.WIFI_STATE_ENABLED:
                binding.wifiSwitch.setChecked(true);
                binding.wifiView.setImageResource(R.drawable.wifi_connected);
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                binding.wifiSwitch.setChecked(false);
                binding.wifiView.setImageResource(R.drawable.wifi);
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
        binding.registerYourDeviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RegisterHotspot.class));
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
            binding.wifiView.setImageResource(R.drawable.wifi_connected);
        } else {
            Toast.makeText(this, "Please Start your WIFI First", Toast.LENGTH_SHORT).show();
            binding.wifiSwitch.setChecked(false);
            binding.wifiView.setImageResource(R.drawable.wifi);
        }

    }

    @Override
    public void UpdateList(List<ScanResult> filtered) {
        adapter.UpdateList(filtered);
    }
}