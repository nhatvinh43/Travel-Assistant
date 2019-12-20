package com.example.doan;

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

import com.example.doan.data.model.CustomAdapter;
import com.example.doan.data.model.ListTour;
import com.example.doan.data.model.Tour;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_topTours extends Fragment {
    private ArrayList<Tour> dataSet = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CustomAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_navigation_top_tours, container, false);


        ImageButton imageButton = view.findViewById(R.id.addTour);
        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), AddTour.class);
                startActivity(intent);
            }
        });

        mShimmerViewContainer = view.findViewById(R.id.shimmerContainer);


        mRecyclerView = (RecyclerView)view.findViewById(R.id.mRecyclerView);
        adapter = new CustomAdapter(getActivity(),dataSet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        fetchItemList();

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
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
        String Token = intent.getStringExtra("token");
        API api = retrofit.getClient().create(API.class);
        Call<ListTour> call1 = api.getListTour(Token,"100");
        call1.enqueue(new Callback<ListTour>() {
            @Override
            public void onResponse(Call<ListTour> call, Response<ListTour> response) {
                Log.d("TAG",response.code()+" ");
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    JsonObject errorGetTopTours = gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                    Toast.makeText(fragment_topTours.this.getContext() ,errorGetTopTours.get("message").getAsString(),Toast.LENGTH_LONG).show();
                    return;
                }
                ListTour resource = response.body();
                ArrayList<Tour> data = resource.getTours();
                int totals = Integer.valueOf(resource.getTotal());

                for (int i = 0; i<data.size();i++){
                    if (data.get(i).getEndDate()==null||data.get(i).getStartDate()==null)
                        continue;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Long tempStartDate = Long.valueOf(data.get(i).getStartDate());
                    String tempStartDateF = sdf.format(new Date(tempStartDate));
                    Long tempEndDate = Long.valueOf(data.get(i).getEndDate());
                    String tempEndDateF = sdf.format(new Date(tempEndDate));
                    Tour temp = new Tour(data.get(i).getId(),data.get(i).getStatus(),data.get(i).getName(),data.get(i).getMinCost(),
                            data.get(i).getMaxCost(), tempStartDateF, tempEndDateF,data.get(i).getAdults(),
                            data.get(i).getChilds(),data.get(i).getIsPrivate(),data.get(i).getAvatar());
                    dataSet.add(temp);

                }


                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListTour> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}
