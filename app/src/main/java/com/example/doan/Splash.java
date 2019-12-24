package com.example.doan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.ui.login.LoginActivity;

public class Splash extends AppCompatActivity {
    private MyApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        app = (MyApplication) getApplication();
        SharedPreferences sharedPreferences = getSharedPreferences("DoAn", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("UserToken","");
        if (!token.equals("")){
            app.userToken = token;
            Log.d("UserTokenSplash", token);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
