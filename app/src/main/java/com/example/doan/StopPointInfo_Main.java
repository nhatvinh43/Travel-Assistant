package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

public class StopPointInfo_Main extends AppCompatActivity implements StopPointInfo_Tab1.OnFragmentInteractionListener, StopPointInfo_Tab2.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_point_info__main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager2);
        com.example.doan.PagerAdapter_StopPoint myPagerAdapter = new com.example.doan.PagerAdapter_StopPoint(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout2);
        tablayout.setupWithViewPager(viewPager);

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
