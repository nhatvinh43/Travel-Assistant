package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


import java.util.Calendar;

public class AddTour extends AppCompatActivity {

    Context context = this;
    private Calendar c;
    private DatePickerDialog dpd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_tour);

        EditText tourName = findViewById(R.id.tourNameText);
        Switch privateSwitch = findViewById(R.id.isPrivate);
        Spinner status = findViewById(R.id.status);
        EditText adultCount = findViewById(R.id.adult);
        EditText childrenCount = findViewById(R.id.children);
        ImageButton startDateButton = findViewById(R.id.startDateButton);
        ImageButton endDateButton = findViewById(R.id.endDateButton);
        final TextView startDate = findViewById(R.id.startDate);
        final TextView endDate = findViewById(R.id.endDate);
        EditText minCost = findViewById(R.id.minCost);
        EditText maxCost = findViewById(R.id.maxCost);
        ImageButton confirmButton = findViewById(R.id.confirmButton);
        ImageButton cancelButton = findViewById(R.id.cancelButton);

        TextView startLocation = findViewById(R.id.startLocation);
        TextView endLocation = findViewById(R.id.endLocation);
        ImageButton startLocationButton = findViewById(R.id.startLocationButton);
        ImageButton endLocationButton = findViewById(R.id.endLocationButton);


        String stringStartDate, stringEndDate;
        stringEndDate = "";
        stringStartDate = "";

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
                Intent intent = new Intent(context,StopPoints.class);
                startActivity(intent);
                finish();
            }
        };
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(AddTour.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate.setText(dayOfMonth +"/"+(month+1)+"/"+year);
                    }
                }, day, month, year);
                dpd.show();
            }
        });
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(AddTour.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate.setText(dayOfMonth +"/"+(month+1)+"/"+year);
                    }
                }, day, month, year);
                dpd.show();
            }

        });

        startLocationButton.setOnClickListener(confirm);
        confirmButton.setOnClickListener(confirm);


        /*Lưu ý:
        * Khi click vào startDateButton sẽ hiện hộp thoại chọn ngày tháng năm, sau khi nhấp xác nhận thì ngày tháng năm được hiển thị trên TextView startDate, endDate cũng tương tự.
        * Nhấp vào confirmButton sẽ tạo tour, tạo intent chuyển sang màn hình thêm điểm dừng.
        * */
    }
}
