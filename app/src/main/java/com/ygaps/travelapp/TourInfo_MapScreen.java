package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class TourInfo_MapScreen extends AppCompatActivity {
    private Dialog eventsDialog;
    private int selectedNotificationType =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info__map_screen);

        ImageButton sendEvents = findViewById(R.id.tourInfoMapSendNotificationsButton);
        sendEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsDialog = new Dialog(TourInfo_MapScreen.this);
                eventsDialog.setContentView(R.layout.dialog_notifications);
                eventsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(eventsDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                eventsDialog.show();
                eventsDialog.getWindow().setAttributes(lp);



                final EditText notificationMsg = eventsDialog.findViewById(R.id.tourInfoDialogMsg);
                final EditText notificationVelocity = eventsDialog.findViewById(R.id.tourInfoDialogVelocity);
                final TextView selectedNotification = eventsDialog.findViewById(R.id.tourInfoDialogSelectedNotification);


                final ImageButton policeStation = eventsDialog.findViewById(R.id.tourInfoDialogPolice);
                policeStation.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        selectedNotificationType=1;
                        selectedNotification.setText(R.string.policeStation);
                        selectedNotification.setVisibility(View.VISIBLE);
                        notificationMsg.setVisibility(View.GONE);
                        notificationVelocity.setVisibility(View.GONE);

                    }
                });
                ImageButton problemsOnRoad = eventsDialog.findViewById(R.id.tourInfoDialogProblems);
                problemsOnRoad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedNotificationType=2;
                        selectedNotification.setText(R.string.problemsOnRoad);
                        selectedNotification.setVisibility(View.VISIBLE);
                        notificationMsg.setVisibility(View.VISIBLE);
                        notificationVelocity.setVisibility(View.GONE);
                    }
                });

                ImageButton speedLimit = eventsDialog.findViewById(R.id.tourInfoDialogSpeedLimit);
                speedLimit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedNotificationType=3;
                        selectedNotification.setText(R.string.speedLimitSign);
                        selectedNotification.setVisibility(View.VISIBLE);
                        notificationMsg.setVisibility(View.GONE);
                        notificationVelocity.setVisibility(View.VISIBLE);
                    }
                });



            }
        });
    }
}
