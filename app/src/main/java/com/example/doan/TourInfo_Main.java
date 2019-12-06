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

import com.google.android.material.tabs.TabLayout;

public class TourInfo_Main extends AppCompatActivity
        implements TourInfo_Tab1.OnFragmentInteractionListener,
        TourInfo_Tab2.OnFragmentInteractionListener, tourInfo_Tab3.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info__main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        com.example.doan.PagerAdapter myPagerAdapter = new com.example.doan.PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(viewPager);

        ImageButton back = findViewById(R.id.tourInfoBack);
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
