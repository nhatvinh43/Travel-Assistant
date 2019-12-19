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

import com.example.doan.data.model.ServiceDetail;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;
import com.example.doan.ui.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StopPointInfo_Main extends AppCompatActivity implements StopPointInfo_Tab1.OnFragmentInteractionListener, StopPointInfo_Tab2.OnFragmentInteractionListener{

    public static String StopPointId = "";
    public static Integer SeeFrom = -1; //0 is from ListStopPoint Intour. 1 is from Map.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_point_info__main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager2);
        com.example.doan.PagerAdapter_StopPoint myPagerAdapter = new com.example.doan.PagerAdapter_StopPoint(getSupportFragmentManager());
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


        Log.d("StopPointInfoMain",StopPointId);

        API api = retrofit.getClient().create(API.class);
        Call<ServiceDetail> call = api.getServiceDetail(LoginActivity.TOKEN,Integer.valueOf(StopPointId));
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
