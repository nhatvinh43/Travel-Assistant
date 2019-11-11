package com.example.doan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.doan.data.model.CustomAdapter;
import com.example.doan.data.model.ListTour;
import com.example.doan.data.model.Tour;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Tour> dataSet = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CustomAdapter adapter;
    private ProgressDialog spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.mRecyclerView);
        adapter = new CustomAdapter(this,dataSet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
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
                Toast.makeText(MainActivity.this, String.valueOf(resource.getTours().size()),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ListTour> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/
    }

}
