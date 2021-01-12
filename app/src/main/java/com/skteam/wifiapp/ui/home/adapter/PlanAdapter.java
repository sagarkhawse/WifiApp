package com.skteam.wifiapp.ui.home.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skteam.wifiapp.R;
import com.skteam.wifiapp.databinding.HotspotNearbyItemBinding;
import com.skteam.wifiapp.databinding.PlanLayoutItemBinding;
import com.skteam.wifiapp.ui.home.model.Plan;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {
    private Context context;
    private List<Plan> planList = new ArrayList<>();
    int numberOfLevels = 5;
    private WifiManager wifiManager;
    public PlanAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PlanLayoutItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.plan_layout_item, parent, false);
        return new PlanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        holder.OnBindData(planList.get(position));
    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    public void UpdateList(List<Plan> planList) {
        this.planList = planList;
        notifyDataSetChanged();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        private PlanLayoutItemBinding binding;

        public PlanViewHolder(@NonNull PlanLayoutItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void OnBindData(Plan plan) {
            binding.availableTime.setText(plan.getTime()+" DAYS");
            binding. price.setText("₹ " +plan.getPrice());
            binding.accessType.setText(plan.getAccess());
        }
    }
}
