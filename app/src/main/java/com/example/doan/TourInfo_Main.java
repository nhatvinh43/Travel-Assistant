package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.data.model.Comment;
import com.example.doan.data.model.LoginData;
import com.example.doan.data.model.Member;
import com.example.doan.data.model.StopPoint;
import com.example.doan.data.model.Tour;
import com.example.doan.data.model.TourInfo;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;
import com.example.doan.ui.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourInfo_Main extends AppCompatActivity
        implements TourInfo_Tab1.OnFragmentInteractionListener,
        TourInfo_Tab2.OnFragmentInteractionListener, tourInfo_Tab3.OnFragmentInteractionListener {

    public static String tourId = "";
    public static int privacy; //this var stored you are Creater or not. See is 0. Own is 1.
    public static TourInfo tourInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info__main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        com.example.doan.PagerAdapter myPagerAdapter = new com.example.doan.PagerAdapter(getSupportFragmentManager());
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
        final TextView tv = findViewById(R.id.tourInfoName);

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
                Toast.makeText(getApplicationContext(),"Delete Click",Toast.LENGTH_SHORT).show();
            }
        });

        if (privacy == 0){
            deleteTour.setVisibility(View.INVISIBLE);
        }

        Log.d("TourInfoMain TourId", privacy + " " + tourId);
        //tv.setText(tourId);
        API api = retrofit.getClient().create(API.class);
        Call<TourInfo> call = api.getTourInfoTV(LoginActivity.TOKEN, Integer.valueOf(tourId));
        call.enqueue(new Callback<TourInfo>() {
            @Override
            public void onResponse(Call<TourInfo> call, Response<TourInfo> response) {
                Log.d("TourInfoMain ResCode", response.code()+"");
                if (!response.isSuccessful()){
                    Log.d("TourInfoMain Succes",response.isSuccessful()+"");
                    return;
                }

                TourInfo tourInfo = response.body();
                Log.d("TourInfoMain TourName", tourInfo.getName() + "Name or Null");
                tv.setText(tourInfo.getName());
            }

            @Override
            public void onFailure(Call<TourInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
