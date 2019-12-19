package com.example.doan.data.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;

import java.util.ArrayList;

public class StopPointAdapter extends RecyclerView.Adapter<StopPointAdapter.StopPointViewHolder> {
    private ArrayList<StopPoint> listStopPoint;
    private Context context;
    private LayoutInflater layoutInflater;

    public StopPointAdapter(Context context, ArrayList<StopPoint> obj){
        this.context = context;
        this.listStopPoint = obj;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class StopPointViewHolder extends RecyclerView.ViewHolder {
        public ImageView mAvatar;
        public TextView mName, mDetail;
        public androidx.cardview.widget.CardView cardView;
        public StopPointViewHolder(View v){
            super(v);
            mAvatar = (ImageView) v.findViewById(R.id.mAvatar);
            mName = (TextView) v.findViewById(R.id.mName);
            mDetail = (TextView) v.findViewById(R.id.mDetail);
            cardView = v.findViewById(R.id.parentSP);
        }
    }

    @Override
    public int getItemCount() {
        return listStopPoint.size();
    }

    @NonNull
    @Override
    public StopPointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = layoutInflater.inflate(R.layout.recyclerview_stop_points, parent, false);
        return new StopPointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StopPointViewHolder holder, int position) {
        final StopPoint s = listStopPoint.get(position);
        holder.mAvatar.setColorFilter(11111);
        holder.mName.setText(s.getName());
        holder.mDetail.setText(s.getAddress());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),"Click On Stop Point ID: " + s.getId(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
