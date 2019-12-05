package com.example.doan;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class PagerAdapter_StopPoint extends FragmentStatePagerAdapter {
    public PagerAdapter_StopPoint(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new StopPointInfo_Tab1();
            case 1: return new StopPointInfo_Tab2();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
}
