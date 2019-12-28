package com.ygaps.travelapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.ListStopPointSetSP;
import com.ygaps.travelapp.data.model.ServiceDetail;
import com.ygaps.travelapp.data.model.StopPointSetSP;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;
import com.ygaps.travelapp.ui.login.LoginActivity;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateStopPoint extends AppCompatActivity {

    private MyApplication app;
    private EditText name, contact, minCost, maxCost;
    private TextView startDateText, endDateText, address;
    private Calendar c;
    private DatePickerDialog dpd;
    private long ArrivalDateTime, LeaveDateTime;
    private String tourId = "";
    private Double lat, lng;
    private String addressDetail = "";
    private ListStopPointSetSP data = new ListStopPointSetSP();
    private StopPointSetSP stopPointSetSP = new StopPointSetSP();
    private Integer serviceTypeId = 0;
    private ImageButton ServiceType1, ServiceType2, Confirm, Cancel, startDate, endDate;
    private Integer NewOrOld = 1;
    private String ServiceId = null;
    private API api = retrofit.getClient().create(API.class);
    private Boolean flag = false;
    //if New: get Lat, Long, Address, ServiceType
    //if Old: add ArrivalDateTime and LeaveDateTime
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stop_point);
        app = (MyApplication) getApplication();

        name = findViewById(R.id.stopPointName);
        contact = findViewById(R.id.stopPointContact);
        minCost = findViewById(R.id.minCostSP);
        maxCost = findViewById(R.id.maxCostSP);

        Confirm = findViewById(R.id.confirmButtonSP);
        Cancel = findViewById(R.id.cancelButtonSP);
        startDate = findViewById(R.id.arrivalDateButton);
        endDate = findViewById(R.id.leaveDateButton);
        ServiceType1 = findViewById(R.id.hotelSP);
        ServiceType2 = findViewById(R.id.restaurantSP);

        startDateText = findViewById(R.id.arrivalDate);
        endDateText = findViewById(R.id.leaveDate);
        address = findViewById(R.id.addressSP);

        ServiceType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceTypeId = 0;
                Toast.makeText(getApplicationContext(),"Hotel", Toast.LENGTH_SHORT).show();
            }
        });
        ServiceType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceTypeId = 1;
                Toast.makeText(getApplicationContext(),"Restaurant", Toast.LENGTH_SHORT).show();
            }
        });

        //prepare data if old
        final Intent intent = getIntent();
        if (intent.hasExtra("NewOrOld")){
            NewOrOld = intent.getIntExtra("NewOrOld", 0);
        }else{
            finish();
        }
        Log.d("CreateSP", NewOrOld + "");

        if (intent.hasExtra("TourId")){
            tourId = intent.getStringExtra("TourId");
        }
        data.setTourId(tourId);
        Log.d("CreateSP", "TourId " + tourId +"/"+data.getTourId());

        if (NewOrOld == 1){
            stopPointSetSP.setId(null);
            stopPointSetSP.setServiceId(null);
            stopPointSetSP.setAvatar(null);
            //get all data
            Log.d("CreateSP", "New");
            if (intent.hasExtra("Address")){
                addressDetail = intent.getStringExtra("Address");
                Log.d("CreateSP", addressDetail);
            }
            if (intent.hasExtra("Latitude")){
                lat = intent.getDoubleExtra("Latitude",0);
                Log.d("CreateSP", lat+"");
            }
            if (intent.hasExtra("Longtitude")){
                lng = intent.getDoubleExtra("Longtitude",0);
                Log.d("CreateSP",lng+"");
            }
            address.setText(addressDetail);
            stopPointSetSP.setAddress(addressDetail);
            stopPointSetSP.setLat(lat);
            stopPointSetSP.set_long(lng);

        }
        if (NewOrOld == 0){
            Log.d("CreateSP", "Old");//co san
            if (intent.hasExtra("ServiceId")){
                ServiceId = intent.getStringExtra("ServiceId");
            }
            stopPointSetSP.setServiceId(ServiceId);
            stopPointSetSP.setId(null);
            stopPointSetSP.setAvatar(null);
            Log.d("CreateSP", "ServiceId " + ServiceId);
            Call<ServiceDetail> call = api.getServiceDetail(app.userToken,Integer.valueOf(ServiceId));
            call.enqueue(new Callback<ServiceDetail>() {
                @Override
                public void onResponse(Call<ServiceDetail> call, Response<ServiceDetail> response) {
                    Log.d("CreateSP", response.code()+" ");
                    if (!response.isSuccessful()){
                        Log.d("CreateSP", response.isSuccessful()+"");
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ServiceDetail data = response.body();
                    stopPointSetSP.setPrivinceId(data.getProvinceId());
                    stopPointSetSP.setServiceTypeId(data.getServiceTypeId());
                    stopPointSetSP.setLat(Double.valueOf(data.getLat()));
                    stopPointSetSP.set_long(Double.valueOf(data.get_long()));
                    stopPointSetSP.setAddress(data.getAddress());
                    if (data.getServiceTypeId()==1){
                        ServiceType2.setVisibility(View.GONE);
                    }
                    if (data.getServiceTypeId()==2){
                        ServiceType1.setVisibility(View.GONE);
                    }
                    name.setText(data.getName());
                    address.setText(data.getAddress());
                    minCost.setText(data.getMinCost());
                    maxCost.setText(data.getMaxCost());
                }
                @Override
                public void onFailure(Call<ServiceDetail> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check validate
                if (name.getText().toString().length() == 0 || ArrivalDateTime == 0 || LeaveDateTime ==0 ||
                        minCost.getText().toString().length() == 0 || maxCost.getText().toString().length() ==0){
                    flag = false;
                }
                else{
                    flag = true;
                }

//                this.id = id;
//                this.serviceId = serviceId;
//                this.name = name;
//                this.privinceId = privinceId;
//                this.lat = lat;
//                this._long = _long;
//                this.arrivalAt = arrivalAt;
//                this.leaveAt = leaveAt;
//                this.serviceTypeId = serviceTypeId;
//                this.minCost = minCost;
//                this.maxCost = maxCost;
//                this.avatar = avatar;

                if (flag){
                    stopPointSetSP.setName(name.getText().toString());
                    stopPointSetSP.setMinCost(Integer.valueOf(minCost.getText().toString()));
                    stopPointSetSP.setMaxCost(Integer.valueOf(maxCost.getText().toString()));
                    stopPointSetSP.setArrivalAt(ArrivalDateTime);
                    stopPointSetSP.setLeaveAt(LeaveDateTime);
                    stopPointSetSP.setServiceTypeId(serviceTypeId);
                    ArrayList<StopPointSetSP>list = new ArrayList<>();
                    list.add(stopPointSetSP);
                    data.setStopPointSetSPS(list);

                    Call<JsonObject>call = api.setStopPoints(app.userToken, data);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("CreateSPSend", response.code()+" "+ response.isSuccessful());
                            if (!response.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot Create", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Cancel", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                dpd = new DatePickerDialog(CreateStopPoint.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String myDayStart = dayOfMonth +"/"+(month+1)+"/"+year;
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
                        startDateText.setText(myDayStart);
                        ArrivalDateTime = milis;
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                dpd = new DatePickerDialog(CreateStopPoint.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String myDayEnd = dayOfMonth +"/"+(month+1)+"/"+year;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = null;
                        try {
                            date = sdf.parse(myDayEnd);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long milis = date.getTime();
                        endDateText.setText(myDayEnd);
                        LeaveDateTime = milis;
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }

        });


        //Lưu ý phần service type: tạo 1 chuỗi trống, mỗi khi click vào button hotel hoặc restaurant thì sẽ gán chuỗi bằng "Hotel" hoặc "Restaurant" tương ứng và cộng chuỗi này vào phần tên Stop point, ví dụ
        // tên người dùng nhập là "ABC" và nhấp vào nút Hotel thì tên hiển thị ra TextView stopPointName  sẽ là "Hotel ABC"

    }

}
