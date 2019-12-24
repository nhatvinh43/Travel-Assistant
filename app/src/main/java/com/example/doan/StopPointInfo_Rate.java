package com.example.doan;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.data.model.FeedbackSend;
import com.example.doan.data.remote.API;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.doan.data.remote.retrofit.getClient;

public class StopPointInfo_Rate extends AppCompatActivity {
    private MyApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_poin_info__rate);
        app = (MyApplication) getApplication();
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
               API api = getClient().create(API.class);
               FeedbackSend feedbackSend = new FeedbackSend(Integer.parseInt(StopPointInfo_Main.StopPointId),
                       comment.getText().toString(),(int)(ratingBar.getRating()));
               Call<JsonObject> call = api.sendFeedback(app.userToken,feedbackSend);
               call.enqueue(new Callback<JsonObject>() {
                   @Override
                   public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                       if (!response.isSuccessful()) {
                           Gson gson = new Gson();
                           JsonObject error =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                           Toast.makeText(StopPointInfo_Rate.this,error.get("message").getAsString(),Toast.LENGTH_LONG).show();
                           return;
                       }
                       JsonObject feedbackResponse = response.body();
                       Toast.makeText(StopPointInfo_Rate.this, feedbackResponse.get("message").toString(), Toast.LENGTH_SHORT).show();
                       finish();
                   }

                   @Override
                   public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("ErrorFeedback",t.getMessage());
                        finish();
                   }
               });
           }
       });

    }
}
