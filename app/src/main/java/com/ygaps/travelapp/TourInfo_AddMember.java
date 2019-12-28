package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.Invitation;
import com.ygaps.travelapp.data.model.Tour;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourInfo_AddMember extends AppCompatActivity {

    private API api = retrofit.getClient().create(API.class);
    private MyApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info__add_member);
        app = (MyApplication) getApplication();

        final EditText userId = findViewById(R.id.tourInfoAddMemberName);

        Button addMem = findViewById(R.id.tourInfoAddMemberSubmit);
        addMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                if (userId.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Please Input UserID", Toast.LENGTH_SHORT).show();
                }
                else{
                    Invitation invitation = new Invitation(TourInfo_Main.tourId, userId.getText().toString(), true);
                    Call<JsonObject> call = api.sendInvitation(app.userToken, invitation);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("SendInvite", response.code()+"|"+TourInfo_Main.tourId+"|"+userId.getText().toString());
                            if (!response.isSuccessful()){
                                return;
                            }
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                finish();
            }
        });

        ImageButton back = findViewById(R.id.tourInfoAddMemberBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
