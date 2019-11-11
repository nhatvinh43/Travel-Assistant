package com.example.doan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doan.data.model.CustomAdapter;
import com.example.doan.data.model.ListTour;
import com.example.doan.data.model.Tour;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    final Fragment fragment1 = new fragment_topTours();
    final Fragment fragment2 = new fragment_myTours();
    final Fragment fragment3 = new fragment_addTour();
    final Fragment fragment4 = new fragment_notification();
    final Fragment fragment5 = new fragment_settings();

    private ProgressDialog spinner;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.frame_container,fragment5).hide(fragment5).commit();
        fragmentManager.beginTransaction().add(R.id.frame_container,fragment4).hide(fragment4).commit();
        fragmentManager.beginTransaction().add(R.id.frame_container,fragment3).hide(fragment3).commit();
        fragmentManager.beginTransaction().add(R.id.frame_container,fragment2).hide(fragment2).commit();
        fragmentManager.beginTransaction().add(R.id.frame_container,fragment1).commit();

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_topTours:
                        fragmentManager.beginTransaction().hide(fragment).add(R.id.frame_container,fragment1);
                        fragment = fragment1;
                        return true;
                    case R.id.navigation_myTours:
                        fragmentManager.beginTransaction().hide(fragment).add(R.id.frame_container,fragment2);
                        fragment = fragment2;
                        return true;
                    case R.id.navigation_addTour:
                        fragmentManager.beginTransaction().hide(fragment).add(R.id.frame_container,fragment3);
                        fragment = fragment3;
                        return true;
                    case R.id.navigation_notifications:
                        fragmentManager.beginTransaction().hide(fragment).add(R.id.frame_container,fragment4);
                        fragment = fragment4;
                        return true;
                    case R.id.navigation_settings:
                        fragmentManager.beginTransaction().hide(fragment).add(R.id.frame_container,fragment5);
                        fragment = fragment5;
                        return true;

                }
                return false;
            }
        });


    }

}
