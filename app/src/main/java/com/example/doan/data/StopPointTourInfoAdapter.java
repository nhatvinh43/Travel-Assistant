package com.example.doan.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.data.model.StopPointTourInfo;

import java.util.ArrayList;

public class StopPointTourInfoAdapter extends RecyclerView.Adapter<StopPointTourInfoAdapter.ViewHolder>{
    private ArrayList<StopPointTourInfo> stopPointTourInfoArrayList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView avatar;
        TextView name;
        TextView detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //avatar = itemView.findViewById(R.id.mAvatar);
            name = itemView.findViewById(R.id.mName);
            detail = itemView.findViewById(R.id.mDetail);
        }
    }

    public StopPointTourInfoAdapter(ArrayList<StopPointTourInfo> stopPointsList) {
        this.stopPointTourInfoArrayList = stopPointsList;
    }

    @NonNull
    @Override
    public StopPointTourInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_stop_points,parent,false);
        return new StopPointTourInfoAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StopPointTourInfoAdapter.ViewHolder holder, int position) {
        StopPointTourInfo stopPointTourInfo = stopPointTourInfoArrayList.get(position);
        holder.name.setText(stopPointTourInfo.getName());
        holder.detail.setText(stopPointTourInfo.getAddress());
    }

    @Override
    public int getItemCount() {
        return stopPointTourInfoArrayList.size();
    }
}
