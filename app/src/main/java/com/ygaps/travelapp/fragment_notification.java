package com.ygaps.travelapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;


public class fragment_notification extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_navigation_notification, container, false);

        mShimmerViewContainer = view.findViewById(R.id.shimmerContainerNotification);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerNotification);
        mShimmerViewContainer.startShimmerAnimation();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
