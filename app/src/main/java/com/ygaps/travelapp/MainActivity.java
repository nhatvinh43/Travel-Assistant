package com.ygaps.travelapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.JsonReader;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.RegisterFCM;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;
import com.ygaps.travelapp.ui.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public String fcmToken;
    private API api = retrofit.getClient().create(API.class);
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

        //code
        try{
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            Log.d("FCM", task.isSuccessful()+"");
                            if (!task.isSuccessful()) {
                                Log.d("FCM", task.getException()+" "+task.isSuccessful());
                                return;
                            }
                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                            fcmToken = token;
                            Log.d("FCM", token);
                        }
                    });
        }catch (IllegalStateException ioe){
            Log.d("FCM", ioe + "");
        }
        final String androidId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.d("FCMRegister", androidId + "/" + app.userToken);

        RegisterFCM registerFCM = new RegisterFCM(app.userToken, androidId, 1, "1.0");
        Call<JsonObject>call = api.registerFCM(app.userToken, registerFCM);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("FCMRegister", response.code()+"|"+app.userToken);
                if (!response.isSuccessful()){
                    return;
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
