package com.ygaps.travelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.TourInfo;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourInfo_Main extends AppCompatActivity
        implements TourInfo_Tab1.OnFragmentInteractionListener,
        TourInfo_Tab2.OnFragmentInteractionListener, tourInfo_Tab3.OnFragmentInteractionListener {

    public static String tourId = "";
    public static int privacy; //this var stored you are Creater or not. See is 0. Own is 1.
    public static TourInfo tourInfo;
    private MyApplication app;
    private static TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info__main);
        app = (MyApplication) getApplication();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        com.ygaps.travelapp.PagerAdapter myPagerAdapter = new com.ygaps.travelapp.PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(viewPager);
        ImageButton deleteTour = findViewById(R.id.tourInfoDeleteTour);
        ImageButton back = findViewById(R.id.tourInfoBack);
        back.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v)
            {
                finish();
            }
        });
        tv = findViewById(R.id.tourInfoName);

        Intent intent = getIntent();
        if (intent.hasExtra("TourIdForInfo")){
            tourId = intent.getStringExtra("TourIdForInfo");
        }
        else{
            finish();
        }
        if (intent.hasExtra("Privacy")){
            privacy = intent.getIntExtra("Privacy",0);
        }
        else{
            finish();
        }
//        Bundle bundle = getIntent().getExtras();
//        if (bundle!=null){
//            tourId = bundle.getString("TourIdForInfo");
//        }
        deleteTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API api = retrofit.getClient().create(API.class);
                Call<JsonObject> call = api.removeTour(app.userToken,tourId,-1);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            JsonObject error =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                            Toast.makeText(getApplicationContext(),error.get("message").getAsString(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(TourInfo_Main.this, "Successs", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });

        if (privacy == 0){
            deleteTour.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static void setTourName(String name){
        tv.setText(name);
    }
}
