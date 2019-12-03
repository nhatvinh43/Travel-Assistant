package com.example.doan;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.doan.TourInfo_Tab1;
import com.example.doan.TourInfo_Tab2;
import com.example.doan.tourInfo_Tab3;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new TourInfo_Tab1();
            case 1: return new TourInfo_Tab2();
            case 2: return new tourInfo_Tab3();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
}
