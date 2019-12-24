package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SavedStopPoints extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_stop_points);

        ImageButton closeWindow = findViewById(R.id.closeWindow);
        View.OnClickListener close = new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        };
        closeWindow.setOnClickListener(close);
    }
}
