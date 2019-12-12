package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doan.data.model.CommentSend;
import com.example.doan.data.remote.API;
import com.example.doan.ui.login.LoginActivity;
import com.facebook.login.Login;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Field;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.doan.data.remote.retrofit.getClient;

public class TourInfo_Rate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info__rate);

        final EditText comment = findViewById(R.id.tourInfoCommentBox);
        Button submit = findViewById(R.id.tourInfoSubmitRate);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = LoginActivity.TOKEN;
                String userId = LoginActivity.USERID;
                Toast.makeText(TourInfo_Rate.this, TourInfo_Main.tourId, Toast.LENGTH_SHORT).show();
                API api = getClient().create(API.class);
                CommentSend commentSend = new CommentSend(TourInfo_Main.tourId,userId,comment.getText().toString());
                Call<JsonObject> call = api.sendComment(token,commentSend);

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(TourInfo_Rate.this,"error Cmt",Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(TourInfo_Rate.this,"ok",Toast.LENGTH_LONG).show();

                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });



        ImageButton back = findViewById(R.id.tourInfoRatingBack);
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
