package com.skteam.wifiapp.ui.home;

import android.net.wifi.ScanResult;

import java.util.List;

public interface HomeNAv {
    void UpdateList(List<ScanResult> filtered);
}
