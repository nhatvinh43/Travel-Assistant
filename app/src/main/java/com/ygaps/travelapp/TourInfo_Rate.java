package com.ygaps.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ygaps.travelapp.data.model.CommentSend;
import com.ygaps.travelapp.data.model.ReviewSend;
import com.ygaps.travelapp.data.remote.API;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ygaps.travelapp.data.remote.retrofit.getClient;

public class TourInfo_Rate extends AppCompatActivity {
    private MyApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info__rate);

        final EditText comment = findViewById(R.id.tourInfoCommentBox);
        Button submit = findViewById(R.id.tourInfoSubmitRate);
        final RatingBar rate = findViewById(R.id.tourInfoRatingBar);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API api = getClient().create(API.class);
                app = (MyApplication) getApplication();
                if ((int)rate.getRating()==0){
                    //send a comment
                    CommentSend cmt = new CommentSend(TourInfo_Main.tourId, app.userToken,comment.getText().toString());
                    Call<JsonObject>call = api.sendComment(app.userToken,cmt);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("TourInfoRate","RESCODECMT"+response.code());
                            if (!response.isSuccessful()){
                                return;
                            }
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                }else{
                    //send a review to a public tour
                    ReviewSend rv = new ReviewSend(Integer.valueOf(TourInfo_Main.tourId),
                            (int)rate.getRating(),comment.getText().toString());
                    Call<JsonObject> call = api.sendReview(app.userToken, rv);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("TourInfoRate","RESCODERV"+response.code());
                            if (!response.isSuccessful()){
                                return;
                            }
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                }

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
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
