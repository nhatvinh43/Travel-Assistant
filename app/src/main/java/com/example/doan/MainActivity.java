package com.example.doan;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements fragment_explore.OnFragmentInteractionListener {
    final Fragment fragment1 = new fragment_topTours();
    final Fragment fragment2 = new fragment_history();
    final Fragment fragment3 = new fragment_explore();
    final Fragment fragment4 = new fragment_notification();
    final Fragment fragment5 = new fragment_settings();
    final Context context = this;

    private ProgressDialog spinner;
    private Fragment fragment = fragment1;
    private FragmentManager fragmentManager;
    public static MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getApplication();
        Log.d("UserTokenAppMainAct",app.userToken);
        setContentView(R.layout.activity_main);

        final BottomNavigationView navView = findViewById(R.id.nav_view);
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
                        fragmentManager.beginTransaction().hide(fragment).show(fragment1).commit();
                        fragment = fragment1;
                        return true;
                    case R.id.navigation_history:
                        fragmentManager.beginTransaction().hide(fragment).show(fragment2).commit();
                        fragment = fragment2;
                        return true;
                    case R.id.navigation_explore:
                        fragmentManager.beginTransaction().hide(fragment).show(fragment3).commit();
                        fragment = fragment3;
                        return true;
                    case R.id.navigation_notifications:
                        fragmentManager.beginTransaction().hide(fragment).show(fragment4).commit();
                        fragment = fragment4;
                        return true;
                    case R.id.navigation_settings:
                        fragmentManager.beginTransaction().hide(fragment).show(fragment5).commit();
                        fragment = fragment5;
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
