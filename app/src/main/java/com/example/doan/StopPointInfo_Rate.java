package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.doan.data.model.FeedbackSend;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;
import com.example.doan.ui.login.LoginActivity;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StopPointInfo_Rate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_poin_info__rate);
        Button send = findViewById(R.id.stopPointInfoSubmitRate);
        final EditText comment = findViewById(R.id.stopPointInfoCommentBox);
        final RatingBar ratingBar = findViewById(R.id.stopPointInfoRatingBar);
        ratingBar.setRating(Float.valueOf(1));
        ImageButton back = findViewById(R.id.stopPointInfoRatingBack);
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                finish();
            }
        });
       send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.d("SPRate Point",(int)ratingBar.getRating()+"");
               Toast.makeText(getApplicationContext(),"Send bang API sengFeedBack", Toast.LENGTH_SHORT).show();
           }
       });

    }
}
