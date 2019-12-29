package com.ygaps.travelapp.data.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.StopPointInfo_Main;

import java.util.ArrayList;

public class StopPointTourInfoAdapter extends RecyclerView.Adapter<StopPointTourInfoAdapter.SPTViewHolder> {
    private ArrayList<StopPointTourInfo> listSP;
    private Context context;
    private LayoutInflater inflater;
    public static int REQUEST_CODE = 111;
    public StopPointTourInfoAdapter(Context context, ArrayList<StopPointTourInfo> obj){
        this.context = context;
        this.listSP = obj;
        this.inflater = LayoutInflater.from(context);
    }

    public class SPTViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView name, detail;
        public androidx.cardview.widget.CardView parentLayout;

        public SPTViewHolder(View view){
            super(view);
            avatar = view.findViewById(R.id.mAvatar);
            name = view.findViewById(R.id.mName);
            detail = view.findViewById(R.id.mDetail);
            parentLayout = view.findViewById(R.id.StopPointItemParent);
        }
    }

    @Override
    public int getItemCount() {
        return listSP.size();
    }

    @NonNull
    @Override
    public SPTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.recyclerview_stop_points, parent, false);
        return new SPTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SPTViewHolder holder, int position) {
        final StopPointTourInfo sp = listSP.get(position);
        holder.name.setText(sp.getName());
        holder.detail.setText(sp.getMinCost()+"-"+sp.getMaxCost());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),"Click On :"+sp.getServiceId()+" "+sp.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, StopPointInfo_Main.class);
                intent.putExtra("StopPointIdForSeeDetail",sp.getServiceId().toString());
                intent.putExtra("SeeFrom",0);
                intent.putExtra("StopPointId",sp.getId().toString());
                context.startActivity(intent);
                //((Activity)context).startActivityForResult(intent,REQUEST_CODE);
                //go to StopPointInfoMain
                //put StopPointId + SeeFrom
            }
        });

    }
    public void clear(){
        listSP.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<StopPointTourInfo> listItems)
    {
        listItems.addAll(listItems);
        notifyDataSetChanged();
    }

}
