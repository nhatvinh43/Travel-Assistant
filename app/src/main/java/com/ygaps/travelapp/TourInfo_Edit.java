package com.ygaps.travelapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.TourInfo;
import com.ygaps.travelapp.data.model.UpdateTourInfoRequest;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourInfo_Edit extends AppCompatActivity {
    Context context = this;
    private Calendar c;
    private DatePickerDialog dpd;

    private static long startDateTime, endDateTime;
    private static String myDayStart, myDayEnd;
    boolean flag = false;

    private MyApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = (MyApplication) getApplication();
        startDateTime = 0;
        endDateTime = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info__edit);
        Intent intent1 = getIntent();
        final String tourID = intent1.getStringExtra("tourId"); // receive after create Tour API Call

        final EditText tourName = findViewById(R.id.tourInfoEditName);
        final Switch privateSwitch = findViewById(R.id.tourInfoEditIsPrivate);
        final Spinner status = findViewById(R.id.tourInfoEditStatus);
        final EditText adultCount = findViewById(R.id.tourInfoEditAdult);
        final EditText childrenCount = findViewById(R.id.tourInfoEditChildren);
        ImageButton startDateButton = findViewById(R.id.tourInfoEditStartDateButton);
        ImageButton endDateButton = findViewById(R.id.tourInfoEditEndDateButton);
        final TextView startDate = findViewById(R.id.tourInfoEditStartDate);
        final TextView endDate = findViewById(R.id.tourInfoEditEndDate);
        final EditText minCost = findViewById(R.id.tourInfoEditMinCost);
        final EditText maxCost = findViewById(R.id.tourInfoEditMaxCost);
        Button confirmButton = findViewById(R.id.tourInfoEditConfirmButton);
        Button cancelButton = findViewById(R.id.tourInfoEditCancelButton);

        View.OnClickListener cancel = new View.OnClickListener(){
            public void onClick(View v)
            {
                finish();
            }
        };

        cancelButton.setOnClickListener(cancel);

        //set data from tour
        API api = retrofit.getClient().create(API.class);
        Call<TourInfo> call = api.getTourInfoTV(((MyApplication)getApplication()).userToken,
                Integer.parseInt(tourID));
        call.enqueue(new Callback<TourInfo>() {
            @Override
            public void onResponse(Call<TourInfo> call, Response<TourInfo> response) {
                Log.d("TourInfoTab1 ResCode", response.code()+"");
                if (!response.isSuccessful()){
                    Log.d("TourInfoTab1 Succes",response.isSuccessful()+"");
                    return;
                }

                TourInfo tourInfo = response.body();
                Log.d("TourInfoTab1 Info", tourInfo.getName() +" "+ tourInfo.getMinCost()
                        + " " + tourInfo.getMaxCost() + " "
                        + tourInfo.getStartDate() + " " + tourInfo.getEndDate() +
                        " " + tourInfo.getChilds() + " " + tourInfo.getAdults());

                tourName.setText(tourInfo.getName());
                minCost.setText(tourInfo.getMinCost());
                maxCost.setText(tourInfo.getMaxCost());
                adultCount.setText(tourInfo.getAdults().toString());
                childrenCount.setText(tourInfo.getChilds().toString());
                status.setSelection(tourInfo.getStatus()+1);
                startDateTime = Long.valueOf(tourInfo.getStartDate());
                endDateTime = Long.valueOf(tourInfo.getEndDate());
                privateSwitch.setChecked(tourInfo.getIsPrivate());
                String startDateF = "";
                String endDateF = "";
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if (tourInfo.getStartDate()== null||tourInfo.getEndDate() == null){
                    startDateF = "Undefined";
                    endDateF = "Undefined";
                }else
                {
                    Long startDateL = Long.valueOf(tourInfo.getStartDate());
                    startDateF = sdf.format(startDateL);
                    Long endDateL = Long.valueOf(tourInfo.getEndDate());
                    endDateF = sdf.format(endDateL);
                    //neu ngay hom nay lon hon ngay ket thuc thi tat nut bat dau
                }
                startDate.setText(startDateF);
                endDate.setText(endDateF);


            }

            @Override
            public void onFailure(Call<TourInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API api = retrofit.getClient().create(API.class);
                String pName = tourName.getText().toString();
                //startDateTime
                //endDateTime
                //LatStart
                //LngStart
                //LatEnd
                //LngEnd
                //isPrivate
                boolean pIsPrivate = privateSwitch.isChecked();
                String countAdults = adultCount.getText().toString();
                String countChilds = childrenCount.getText().toString();
                String pMinCost = minCost.getText().toString();
                String pMaxCost = maxCost.getText().toString();
                int edit_status = status.getSelectedItemPosition() - 1;
                int numberOAd, numberOCh, minB, maxB;
                if (countAdults.length() == 0){
                    numberOAd = 0;
                }
                else {
                    numberOAd = Integer.parseInt(countAdults);
                }

                if (countChilds.length() == 0){
                    numberOCh = 0;
                }
                else {
                    numberOCh = Integer.parseInt(countChilds);
                }
                if (pMinCost.length() == 0){
                    minB = 0;
                }
                else{
                    minB = Integer.parseInt(pMinCost);
                }

                if (pMaxCost.length() == 0){
                    maxB = 0;
                }
                else {
                    maxB = Integer.parseInt(pMaxCost);
                }

                if (pName.length() == 0 || startDateTime == 0 || endDateTime == 0){
                    flag = false;
                }
                if (pName.length() != 0 && startDateTime != 0 && endDateTime != 0){
                    flag = true;
                }
                if (flag){
                    String res = pName + minB + maxB + startDateTime + endDateTime + numberOAd + numberOCh + pIsPrivate;
                    Log.d("Data0", res);
                    UpdateTourInfoRequest updateTourInfoRequest = new UpdateTourInfoRequest(tourID,
                            pName,startDateTime,endDateTime,numberOAd,numberOCh,minB,maxB,pIsPrivate,
                            edit_status);
                    Call<JsonObject> call = api.updateTour(app.userToken, updateTourInfoRequest);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (!response.isSuccessful()) {
                                Gson gson = new Gson();
                                JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                                Toast.makeText(context,errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("ErrorOnEditTourInfo",t.getMessage());
                        }
                    });
                    Intent intent = new Intent();
                    intent.putExtra("name",pName);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Cannot Update", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                dpd = new DatePickerDialog(TourInfo_Edit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myDayStart = dayOfMonth +"/"+(month+1)+"/"+year;
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = null;
                        try {
                            date = sdf.parse(myDayStart);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long milis = date.getTime();
                        startDate.setText(myDayStart);
                        startDateTime = milis;
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                dpd = new DatePickerDialog(TourInfo_Edit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myDayEnd = dayOfMonth +"/"+(month+1)+"/"+year;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = null;
                        try {
                            date = sdf.parse(myDayEnd);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long milis = date.getTime();
                        endDate.setText(myDayEnd);
                        endDateTime = milis;
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }

        });
    }
}