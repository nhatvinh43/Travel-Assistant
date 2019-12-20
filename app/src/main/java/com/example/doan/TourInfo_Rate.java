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

import com.example.doan.data.model.CommentSend;
import com.example.doan.data.model.ReviewSend;
import com.example.doan.data.model.TourInfo;
import com.example.doan.data.remote.API;
import com.example.doan.ui.login.LoginActivity;
import com.google.gson.JsonObject;

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
        final RatingBar rate = findViewById(R.id.tourInfoRatingBar);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API api = getClient().create(API.class);
                if ((int)rate.getRating()==0){
                    //send a comment
                    CommentSend cmt = new CommentSend(TourInfo_Main.tourId, LoginActivity.USERID,comment.getText().toString());
                    Call<JsonObject>call = api.sendComment(LoginActivity.TOKEN,cmt);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("TourInfoRate","RESCODECMT"+response.code());
                            if (!response.isSuccessful()){
                                return;
                            }
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                }else{
                    //send a review to a public tour
                    ReviewSend rv = new ReviewSend(Integer.valueOf(TourInfo_Main.tourId),
                            (int)rate.getRating(),comment.getText().toString());
                    Call<JsonObject> call = api.sendReview(LoginActivity.TOKEN, rv);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("TourInfoRate","RESCODERV"+response.code());
                            if (!response.isSuccessful()){
                                return;
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                }
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
