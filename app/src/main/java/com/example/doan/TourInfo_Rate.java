package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TourInfo_Rate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info__rate);

        ImageButton back = findViewById(R.id.tourInfoRatingBack);
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
