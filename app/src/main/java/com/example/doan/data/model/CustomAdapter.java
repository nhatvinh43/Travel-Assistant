package com.example.doan.data.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<Tour> tourList;
    private Context context;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<Tour> obj){
        this.context = context;
        this.tourList = obj;
        this.inflater = LayoutInflater.from(context);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        public ImageView mTourImage;
        public TextView mTourName, mTourPrice, mTourStartDate, mTourPeoplePH, mTourEndDate;

        public CustomViewHolder(View view){
            super(view);
            mTourImage = (ImageView)view.findViewById(R.id.tourImage);
            mTourName = (TextView) view.findViewById(R.id.tourName);
            mTourPrice = (TextView) view.findViewById(R.id.tourPrice);
            mTourStartDate = (TextView) view.findViewById(R.id.tourStartDate);
            mTourPeoplePH = (TextView) view.findViewById(R.id.tourPeople);
            mTourEndDate = (TextView)view.findViewById(R.id.tourEndDate);
            //mTourImage .... avatar return url
        }
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.recyclerview_item,parent,false );
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Tour t = tourList.get(position);
        String tempPrice = t.getMinCost() + "-" + t.getMaxCost();
        holder.mTourPrice.setText(tempPrice);
        holder.mTourName.setText(t.getName());
        holder.mTourStartDate.setText(t.getStartDate());
        holder.mTourEndDate.setText(t.getEndDate());
        String tempPP = t.getAdults() + " Aldults + " +t.getChilds()+" Childs";
        holder.mTourPeoplePH.setText(tempPP);
        holder.mTourImage.setColorFilter(000000);
    }
}
