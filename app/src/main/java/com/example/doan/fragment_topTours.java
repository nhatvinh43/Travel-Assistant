package com.example.doan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.data.model.CustomAdapter;
import com.example.doan.data.model.ListTour;
import com.example.doan.data.model.Tour;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_topTours extends Fragment {
    private ArrayList<Tour> dataSet = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CustomAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fragment_top_tours, container, false);


        mRecyclerView = (RecyclerView)view.findViewById(R.id.mRecyclerView);
        adapter = new CustomAdapter(getActivity(),dataSet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        API api = retrofit.getClient().create(API.class);
        Call<ListTour> call1 = api.getListTour(API.authKey,"1");
        call1.enqueue(new Callback<ListTour>() {
            @Override
            public void onResponse(Call<ListTour> call, Response<ListTour> response) {
                Log.d("TAG",response.code()+" ");
                ListTour resource = response.body();
                ArrayList<Tour> data = resource.getTours();
                for (Tour tour : data){
                    Tour temp = new Tour(tour.getId(),tour.getStatus(),tour.getName(),tour.getMinCost(),tour.getMaxCost(),
                            tour.getStartDate(),tour.getEndDate(),tour.getAdults(),tour.getChilds(),tour.getIsPrivate(),tour.getAvatar());
                    dataSet.add(temp);
                }
                //loading.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListTour> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
