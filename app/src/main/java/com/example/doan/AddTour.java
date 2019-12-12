package com.example.doan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.GetChars;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.doan.data.model.StopPoint;
import com.example.doan.data.model.Tour;
import com.example.doan.data.model.TourCreate;
import com.example.doan.data.model.TourResFromTourCreate;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;
import com.example.doan.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTour extends AppCompatActivity {

    static public ArrayList<String> listStopPoint = null;
    //write a function to get ID of Stop Point from LatLng of marker
    //ic_hotel ic_burger
    Context context = this;
    private Calendar c;
    private DatePickerDialog dpd;

    private static double LatStart, LatEnd, LngStart, LngEnd;
    private static int startDateTime, endDateTime;
    private static String myDayStart, myDayEnd;
    private static String addressLocationStart, addressLocationEnd;
    boolean flag = false;
    private String tourID = ""; // receive after create Tour
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startDateTime = 0;
        endDateTime = 0;
        LatStart = LatEnd = LngStart = LngEnd = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_tour);

        final EditText tourName = findViewById(R.id.tourNameText);
        final Switch privateSwitch = findViewById(R.id.isPrivate);
        Spinner status = findViewById(R.id.status);
        final EditText adultCount = findViewById(R.id.adult);
        final EditText childrenCount = findViewById(R.id.children);
        ImageButton startDateButton = findViewById(R.id.startDateButton);
        ImageButton endDateButton = findViewById(R.id.endDateButton);
        final TextView startDate = findViewById(R.id.startDate);
        final TextView endDate = findViewById(R.id.endDate);
        final EditText minCost = findViewById(R.id.minCost);
        final EditText maxCost = findViewById(R.id.maxCost);
        Button confirmButton = findViewById(R.id.confirmButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        ImageButton startLocationButton = findViewById(R.id.startLocationButton);
        ImageButton endLocationButton = findViewById(R.id.endLocationButton);


        View.OnClickListener cancel = new View.OnClickListener(){
            public void onClick(View v)
            {
                finish();
            }
        };

        cancelButton.setOnClickListener(cancel);

        View.OnClickListener confirm = new View.OnClickListener(){

            public void onClick(View v)
            {
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
                //avatar

                if (pName.length() == 0 || startDateTime == 0 || endDateTime == 0 ||
                LatStart == 0 || LatEnd == 0){
                    flag = false;
                }
                if (pName.length() != 0 && startDateTime != 0 && endDateTime != 0
                && LatStart != 0 && LatEnd != 0){
                    flag = true;
                }
                if (flag){
                    String res = pName + minB + maxB + startDateTime + endDateTime + numberOAd + numberOCh + LatStart + LngStart
                            + LatEnd + LngEnd + pIsPrivate;
                    Toast.makeText(getApplicationContext(), res,Toast.LENGTH_SHORT).show();
                    Log.d("Data0", res);
                    //Toast.makeText(getApplicationContext(),"Create Tour Success", Toast.LENGTH_SHORT).show();
                    //TourCreate newTour = new TourCreate(pName, startDateTime, endDateTime, LatStart, LngStart,
                    //        LatEnd, LngEnd, pIsPrivate, numberOAd, numberOCh, minB, maxB, "avatar");
                    TourCreate newTour = new TourCreate("test",-255797632,-255797632,10.1231,100.1231,
                            10.1233,100.1231,true,2,1,100,100,"test");
                    Call<JsonObject> call = api.createTour(LoginActivity.TOKEN, newTour);

                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("ADDTOURCODE", response.code()+" ");
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent intent = new Intent(context,StopPoints.class);
                    //intent.putExtra("TourIDFromCreate", tourID);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Cannot Create", Toast.LENGTH_SHORT).show();
                }

            }
        };
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                dpd = new DatePickerDialog(AddTour.this, new DatePickerDialog.OnDateSetListener() {
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
                        startDateTime = (int)milis;
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
                dpd = new DatePickerDialog(AddTour.this, new DatePickerDialog.OnDateSetListener() {
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
                        endDateTime = (int)milis;
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }

        });


        startLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StartEndLocationSelect.class);
                startActivityForResult(intent,1);
            }
        });
        endLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StartEndLocationSelect.class);
                startActivityForResult(intent,2);
            }
        });
        confirmButton.setOnClickListener(confirm);



        /*Lưu ý:
        * Khi click vào startDateButton sẽ hiện hộp thoại chọn ngày tháng năm, sau khi nhấp xác nhận thì ngày tháng năm được hiển thị trên TextView startDate, endDate cũng tương tự.
        * Nhấp vào confirmButton sẽ tạo tour, tạo intent chuyển sang màn hình thêm điểm dừng.
        * */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:{
               if (resultCode==RESULT_OK){

                   LatStart = data.getDoubleExtra("LAT",0);
                   LngStart =data.getDoubleExtra("LONG",0);
                   addressLocationStart = data.getStringExtra("ADDRESSLOCATION");
                   TextView locationStart = (TextView) findViewById(R.id.startLocation);
                   locationStart.setText(addressLocationStart);
                   Toast.makeText(getApplicationContext(),LatStart + " " + LngStart,
                           Toast.LENGTH_SHORT).show();
               }
               break;
            }
            case 2:{
                if (resultCode==RESULT_OK){
                    Toast.makeText(getApplicationContext(),data.getDoubleExtra("LAT",0) + " " + data.getDoubleExtra("LONG",0),
                            Toast.LENGTH_SHORT).show();
                    LatEnd  = data.getDoubleExtra("LAT",0);
                    LngEnd =data.getDoubleExtra("LONG",0);
                    addressLocationEnd = data.getStringExtra("ADDRESSLOCATION");
                    TextView locationEnd = (TextView) findViewById(R.id.endLocation);
                    locationEnd.setText(addressLocationEnd);

                }
                break;
            }
        }
    }
}
