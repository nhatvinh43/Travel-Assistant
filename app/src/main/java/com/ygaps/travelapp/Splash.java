package com.ygaps.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ygaps.travelapp.ui.login.LoginActivity;

public class Splash extends AppCompatActivity {
    private MyApplication app;
    private int timer = 1500;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                app = (MyApplication) getApplication();
                SharedPreferences sharedPreferences = getSharedPreferences("DoAn", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("UserToken","");
                if (!token.equals("")){
                    app.userToken = token;
                    Log.d("UserTokenSplash", token);
                    Intent intent = new Intent(context,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 600);
    }
}
