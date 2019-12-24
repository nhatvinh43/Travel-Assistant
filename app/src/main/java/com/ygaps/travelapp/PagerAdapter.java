package com.ygaps.travelapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new TourInfo_Tab1();
            case 1: return new TourInfo_Tab2();
            case 2: return new tourInfo_Tab3();
            case 3: return new TourInfo_Tab4();
            case 4: return new TourInfo_Tab5();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 5;
    }
}
