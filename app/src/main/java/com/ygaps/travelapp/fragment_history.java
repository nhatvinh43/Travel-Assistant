package com.ygaps.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ygaps.travelapp.data.model.TourMyTourAdapter;
import com.ygaps.travelapp.data.model.ListTourMyTour;
import com.ygaps.travelapp.data.model.TourMyTour;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_history extends Fragment {
    private ArrayList<TourMyTour> dataSet = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TourMyTourAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_navigation_history, container, false);
        Log.d("UserTokenAppFragmentHi",MainActivity.app.userToken);
        ImageButton imageButton = view.findViewById(R.id.historyAddTour);
        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), AddTour.class);
                startActivity(intent);
            }
        });

        mShimmerViewContainer = view.findViewById(R.id.shimmerContainer2);


        mRecyclerView = (RecyclerView)view.findViewById(R.id.mRecyclerView2);
        adapter = new TourMyTourAdapter(getActivity(),dataSet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //fetchItemList();



        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer2);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                mShimmerViewContainer.setVisibility(View.GONE);
                fetchItemList();
                adapter.addAll(dataSet);
                swipeContainer.setRefreshing(false);
            }
        });


        return view;
    }

    public void fetchItemList(){
        mShimmerViewContainer.startShimmerAnimation();
        Intent intent = getActivity().getIntent();
        String Token = MainActivity.app.userToken;
        dataSet.clear();

        API api = retrofit.getClient().create(API.class);
        Log.d("UserToken",Token);
        Call<ListTourMyTour> call1 = api.getHistoryTour(Token,1,30);
        call1.enqueue(new Callback<ListTourMyTour>() {
            @Override
            public void onResponse(Call<ListTourMyTour> call, Response<ListTourMyTour> response) {
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                    Toast.makeText(fragment_history.this.getContext() ,errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                    return;
                }
                ListTourMyTour resource = response.body();

                ArrayList<TourMyTour> data = resource.getTours();
                for (int i=0;i<data.size();i++){
                    TourMyTour tour = data.get(i);
                    if (data.get(i).getEndDate()==null||data.get(i).getStartDate()==null)
                        continue;
                    if (data.get(i).getStatus()==-1){
                        continue;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Long tempStartDate = Long.valueOf(data.get(i).getStartDate());
                    String tempStartDateF = sdf.format(new Date(tempStartDate));
                    Long tempEndDate = Long.valueOf(data.get(i).getEndDate());
                    String tempEndDateF = sdf.format(new Date(tempEndDate));
                    tour.setStartDate(tempStartDateF);
                    tour.setEndDate(tempEndDateF);
                    dataSet.add(tour);
                }
                //loading.dismiss();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListTourMyTour> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        fetchItemList();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}
