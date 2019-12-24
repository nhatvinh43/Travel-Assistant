package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class TourInfo_MapScreen extends AppCompatActivity {
    private Dialog eventsDialog;

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
            }
        });
    }
}
