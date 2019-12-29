package com.ygaps.travelapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.ListStopPointSetSP;
import com.ygaps.travelapp.data.model.StopPointSetSP;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StopPointInfo_Edit extends AppCompatActivity {
    private String Id;
    private String ServiceId;
    private String lat, _long, minCost, maxCost, contact, name,address,arrivalAt,leaveAt,tourId;
    private EditText EditName, EditContact, EditAddress, EditMinCost, EditMaxCost;
    private TextView EditArrival, EditLeave;
    private ImageButton btnArrival,btnLeave, hotel, restaurant, confirm;
    private Integer serviceTypeId, provinceId;
    private Calendar c;
    private DatePickerDialog dpd;
    private long ArrivalLong,LeaveLong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_point_info__edit);
        EditName = findViewById(R.id.stopPointInfoEditName);
        EditContact = findViewById(R.id.stopPointInfoEditContact);
        EditAddress = findViewById(R.id.stopPointInfoEditAddressSP);
        EditArrival = findViewById(R.id.stopPointInfoEditArrivalDate);
        EditLeave = findViewById(R.id.stopPointInfoEditLeaveDate);
        btnArrival = findViewById(R.id.stopPointInfoEditArrivalDateButton);
        btnLeave = findViewById(R.id.stopPointInfoEditLeaveDateButton);
        EditMinCost = findViewById(R.id.stopPointInfoEditMinCostSP);
        EditMaxCost = findViewById(R.id.stopPointInfoEditMaxCostSP);
        hotel = findViewById(R.id.stopPointInfoEditHotelSP);
        restaurant = findViewById(R.id.stopPointInfoEditRestaurantSP);
        confirm = findViewById(R.id.stopPointInfoEditConfirmButtonSP);
        final MyApplication app = (MyApplication) getApplication();

        Id = StopPointInfo_Main.Id;
        ServiceId = StopPointInfo_Main.StopPointId;
        Intent intent = getIntent();
        lat = intent.getStringExtra("lat");
        _long = intent.getStringExtra("long");
        minCost = intent.getStringExtra("minCost");
        maxCost = intent.getStringExtra("maxCost");
        contact = intent.getStringExtra("contact");
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        ArrivalLong = Long.valueOf(intent.getStringExtra("arrivalAt"));
        LeaveLong = Long.valueOf(intent.getStringExtra("leaveAt"));
        serviceTypeId = intent.getIntExtra("serivceTypeId",0);
        tourId = intent.getStringExtra("tourId");
        provinceId = intent.getIntExtra("provinceId",0);
        Log.d("StopPointInfo",Id +" "+ ServiceId +" "+ lat +" "+ _long +" "+ minCost +" "
                + maxCost +" " + contact + " " +name +" "+ address +" "+ arrivalAt+" " + leaveAt +" "+ tourId);

        EditName.setText(name);
        EditContact.setText(contact);
        EditAddress.setText(address);
        EditMinCost.setText(minCost);
        EditMaxCost.setText(maxCost);

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceTypeId = 0;
                Toast.makeText(getApplicationContext(),"Hotel", Toast.LENGTH_SHORT).show();
            }
        });
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceTypeId = 1;
                Toast.makeText(getApplicationContext(),"Restaurant", Toast.LENGTH_SHORT).show();
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        arrivalAt = sdf.format(new Date(ArrivalLong));
        EditArrival.setText(arrivalAt);

        leaveAt = sdf.format(new Date(LeaveLong));
        EditLeave.setText(leaveAt);

        btnArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                dpd = new DatePickerDialog(StopPointInfo_Edit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        arrivalAt = dayOfMonth +"/"+(month+1)+"/"+year;
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = null;
                        try {
                            date = sdf.parse(arrivalAt);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long milis = date.getTime();
                        EditArrival.setText(arrivalAt);
                        ArrivalLong = milis;
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                dpd = new DatePickerDialog(StopPointInfo_Edit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        leaveAt = dayOfMonth +"/"+(month+1)+"/"+year;
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = null;
                        try {
                            date = sdf.parse(leaveAt);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long milis = date.getTime();
                        EditLeave.setText(leaveAt);
                        LeaveLong = milis;
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                name = EditName.getText().toString();
                contact = EditContact.getText().toString();
                address = EditAddress.getText().toString();
                minCost = EditMinCost.getText().toString();
                maxCost = EditMaxCost.getText().toString();
                if (!name.equals("") && !minCost.equals("") && !maxCost.equals("")){
                    flag = true;
                }
                if (flag){
                    API api = retrofit.getClient().create(API.class);
                    ListStopPointSetSP data = new ListStopPointSetSP();
                    data.setTourId(tourId);
                    StopPointSetSP stopPointSetSP = new StopPointSetSP(Id,ServiceId,name,provinceId,
                            Double.valueOf(lat),Double.valueOf(_long),ArrivalLong,LeaveLong,serviceTypeId,
                            Integer.valueOf(minCost), Integer.valueOf(maxCost),address,null);
                    ArrayList<StopPointSetSP> arrayList = new ArrayList<>();
                    arrayList.add(stopPointSetSP);
                    data.setStopPointSetSPS(arrayList);
                    Call<JsonObject> call = api.setStopPoints(app.userToken,data);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (!response.isSuccessful()) {
                                Gson gson = new Gson();
                                JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                                Toast.makeText(getApplicationContext(),errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                                return;
                            }
                            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                            StopPointInfo_Tab1.arrivalAt = String.valueOf(ArrivalLong);
                            StopPointInfo_Tab1.leaveAt = String.valueOf(LeaveLong);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                }else{
                    Toast.makeText(app, "Cannot update", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
