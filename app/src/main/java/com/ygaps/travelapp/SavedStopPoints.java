package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.ygaps.travelapp.data.model.StopPointTourInfo;
import com.ygaps.travelapp.data.model.StopPointTourInfoAdapter;
import com.ygaps.travelapp.data.model.TourInfo;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedStopPoints extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StopPointTourInfoAdapter adapter;
    private ArrayList<StopPointTourInfo> dataSet = new ArrayList<>();
    private String tourId = "";
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_stop_points);
        app = (MyApplication) getApplication();
        ImageButton closeWindow = findViewById(R.id.closeWindow);
        View.OnClickListener close = new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        };
        closeWindow.setOnClickListener(close);
        Intent intent = getIntent();
        if (intent.hasExtra("TourId")){
            tourId = intent.getStringExtra("TourId");
        }else{
            finish();
        }

        recyclerView = findViewById(R.id.savedStopPointsList);
        adapter = new StopPointTourInfoAdapter(this, dataSet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SavedStopPoints.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        fetchStopPointData();
    }
    public void fetchStopPointData(){
        dataSet.clear();
        final API api = retrofit.getClient().create(API.class);
        Call<TourInfo> call = api.getTourInfoTV(app.userToken,Integer.valueOf(tourId));
        call.enqueue(new Callback<TourInfo>() {
            @Override
            public void onResponse(Call<TourInfo> call, Response<TourInfo> response) {
                Log.d("TourInfoTab2", "RESCODE" + response.code());
                if (!response.isSuccessful()){
                    return;
                }
                TourInfo resource = response.body();
                ArrayList<StopPointTourInfo> data = resource.getStopPoints();
                for (int i =0 ; i<data.size();i++){
                    StopPointTourInfo temp = new StopPointTourInfo(data.get(i).getId(),data.get(i).getServiceId(), data.get(i).getAddress(),
                            data.get(i).getProvinceId(), data.get(i).getName(),data.get(i).getLat(), data.get(i).getLong(), data.get(i).getArrivalAt(),
                            data.get(i).getLeaveAt(), data.get(i).getMinCost(),data.get(i).getMaxCost(),data.get(i).getServiceTypeId(),
                            data.get(i).getAvatar(), data.get(i).getIndex());
                    dataSet.add(temp);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<TourInfo> call, Throwable t) {

            }
        });
    }
}
