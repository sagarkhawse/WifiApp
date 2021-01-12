package com.skteam.wifiapp.ui.home.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skteam.wifiapp.R;
import com.skteam.wifiapp.databinding.HotspotNearbyItemBinding;

import java.util.ArrayList;
import java.util.List;

public class WifiNearbyAdapter extends RecyclerView.Adapter<WifiNearbyAdapter.HotsoptViewHolder> {
    private Context context;
    private List<ScanResult> filtered = new ArrayList<>();
    int numberOfLevels = 5;
    private WifiManager wifiManager;
    public WifiNearbyAdapter(Context context, WifiManager wifiManager) {
        this.context = context;
        this.wifiManager=wifiManager;
    }

    @NonNull
    @Override
    public HotsoptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HotspotNearbyItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.hotspot_nearby_item, parent, false);
        return new HotsoptViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HotsoptViewHolder holder, int position) {
        holder.OnBindData(filtered.get(position));
    }

    @Override
    public int getItemCount() {
        return filtered.size();
    }

    public void UpdateList(List<ScanResult> filtered) {
        this.filtered = filtered;
        notifyDataSetChanged();
    }

    class HotsoptViewHolder extends RecyclerView.ViewHolder {
        private HotspotNearbyItemBinding binding;

        public HotsoptViewHolder(@NonNull HotspotNearbyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void OnBindData(ScanResult scanResult) {
            binding.hotsoptSsId.setText(scanResult.SSID);
            int level = WifiManager.calculateSignalLevel(scanResult.level, 5);

        }
    }
}
