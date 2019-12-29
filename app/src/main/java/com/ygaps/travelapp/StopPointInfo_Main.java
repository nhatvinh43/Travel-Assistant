package com.ygaps.travelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.ServiceDetail;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StopPointInfo_Main extends AppCompatActivity implements StopPointInfo_Tab1.OnFragmentInteractionListener, StopPointInfo_Tab2.OnFragmentInteractionListener{
    public static String StopPointId = "";
    public static Integer SeeFrom = -1; //0 is from ListStopPoint Intour. 1 is from Map.
    public static String Id = "";
    private MyApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_point_info__main);
        app = (MyApplication) getApplication();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager2);
        com.ygaps.travelapp.PagerAdapter_StopPoint myPagerAdapter = new com.ygaps.travelapp.PagerAdapter_StopPoint(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout2);
        tablayout.setupWithViewPager(viewPager);

        final TextView SpName = findViewById(R.id.stopPointInfoName);
        Intent intent = getIntent();
        if (intent.hasExtra("StopPointIdForSeeDetail")){
            StopPointId = intent.getStringExtra("StopPointIdForSeeDetail");
        }
        if (intent.hasExtra("SeeFrom")){
            SeeFrom = intent.getIntExtra("SeeFrom",-1);
        }
        if (intent.hasExtra("StopPointId")){
            Id = intent.getStringExtra("StopPointId");
        }

        Log.d("StopPointInfoMain",StopPointId);
        Log.d("StopPointInfoMain",Id);

        API api = retrofit.getClient().create(API.class);
        Call<ServiceDetail> call = api.getServiceDetail(app.userToken,Integer.valueOf(StopPointId));
        call.enqueue(new Callback<ServiceDetail>() {
            @Override
            public void onResponse(Call<ServiceDetail> call, Response<ServiceDetail> response) {
                Log.d("SPInfoMain resCode",response.code()+"");
                if (!response.isSuccessful()){
                    Log.d("SPInforMain Suc", response.isSuccessful()+"");
                    return;
                }
                ServiceDetail serviceDetail = response.body();
                SpName.setText(serviceDetail.getName());
            }

            @Override
            public void onFailure(Call<ServiceDetail> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton btnEditSPInfo = findViewById(R.id.stopPointInfoDeleteTour);
        btnEditSPInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API api = retrofit.getClient().create(API.class);
                Call<JsonObject> call = api.removeStopPoint(app.userToken,Id);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            JsonObject error =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                            Toast.makeText(getApplicationContext(),error.get("message").getAsString(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        JsonObject loginResponse = response.body();
                        Toast.makeText(StopPointInfo_Main.this,
                                loginResponse.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        setResult(RESULT_OK,intent1);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("ErrorOnStopPointInfo",t.getMessage());
                    }
                });
            }
        });

        ImageButton back = findViewById(R.id.stopPointInfoBack);
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                finish();
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
