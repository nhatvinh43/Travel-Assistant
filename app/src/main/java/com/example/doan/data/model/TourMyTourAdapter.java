package com.example.doan.data.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.TourInfo_Main;

import java.util.ArrayList;

public class TourMyTourAdapter extends RecyclerView.Adapter<TourMyTourAdapter.ViewHolder>{
    private ArrayList<TourMyTour> tourMyTourArrayList;
    private Context context;
    private LayoutInflater inflater;
    @NonNull
    @Override
    public TourMyTourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.recyclerview_item,parent,false );
        return new TourMyTourAdapter.ViewHolder(view);
    }

    public TourMyTourAdapter(Context context,ArrayList<TourMyTour> tourMyTourArrayList) {
        this.tourMyTourArrayList = tourMyTourArrayList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(@NonNull TourMyTourAdapter.ViewHolder holder, int position) {
        final TourMyTour tour = tourMyTourArrayList.get(position);
        TourMyTour t = tourMyTourArrayList.get(position);
        String tempPrice = t.getMinCost() + "-" + t.getMaxCost();
        holder.mTourPrice.setText(tempPrice);
        holder.mTourName.setText(t.getName());
        holder.mTourStartDate.setText(t.getStartDate());
        holder.mTourEndDate.setText(t.getEndDate());
        String adults = "max "+t.getAdults();
        holder.mTourPeople.setText(adults);
        String child = "max "+t.getChilds();
        holder.mTourChildren.setText(child);
        holder.mTourImage.setColorFilter(000000);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click " + tour.getId(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, TourInfo_Main.class);
                Log.d("SEND", tour.getId()+"123");
                intent.putExtra("TourIdForInfo", tour.getId().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tourMyTourArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mTourImage;
        public TextView mTourName, mTourPrice, mTourStartDate, mTourPeople, mTourChildren, mTourEndDate;
        public androidx.cardview.widget.CardView cardView;
        public ViewHolder(@NonNull View view) {
            super(view);
            mTourImage = (ImageView)view.findViewById(R.id.tourImage);
            mTourName = (TextView) view.findViewById(R.id.tourName);
            mTourPrice = (TextView) view.findViewById(R.id.tourPrice);
            mTourStartDate = (TextView) view.findViewById(R.id.tourStartDate);
            mTourPeople = (TextView) view.findViewById(R.id.tourPeople);
            mTourChildren = (TextView) view.findViewById(R.id.tourPeopleChildren);
            mTourEndDate = (TextView)view.findViewById(R.id.tourEndDate);
            mTourImage = (ImageView)view.findViewById(R.id.tourImage);
            //mTourImage .... avatar return url
            cardView = view.findViewById(R.id.parentLayout);
        }
    }

    public void clear(){
        tourMyTourArrayList.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<TourMyTour> listItems)
    {
        tourMyTourArrayList.addAll(listItems);
        notifyDataSetChanged();
    }
}
