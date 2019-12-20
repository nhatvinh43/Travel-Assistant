package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateStopPoint extends AppCompatActivity {

    EditText mSPName, maddressSP, minCostSP, maxCostSP;
    TextView mAD, mLD;
    ImageButton mADB, mLDB;
    Button mConBSP, mCanBSP;
    private Calendar c;
    private DatePickerDialog dpd;
    private long ArrivalDateTime, LeaveDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stop_point);
        EditText mSPName = (EditText)findViewById(R.id.stopPointName);
        EditText maddressSP = (EditText)findViewById(R.id.addressSP);
        EditText minCostSP = (EditText)findViewById(R.id.minCostSP);
        EditText maxCostSP = (EditText) findViewById(R.id.maxCostSP);
        ImageButton mConBSP = (ImageButton)findViewById(R.id.confirmButtonSP);
        ImageButton mCanBSP = (ImageButton) findViewById(R.id.cancelButtonSP);
        ImageButton mADB = (ImageButton)findViewById(R.id.arrivalDateButton);
        ImageButton mLBD = (ImageButton)findViewById(R.id.leaveDateButton);
        final TextView mAD= (TextView)findViewById(R.id.arrivalDate);
        final TextView mLD = (TextView)findViewById(R.id.leaveDate);

        mConBSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Confirm", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mCanBSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Cancel", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mADB.setOnClickListener(new View.OnClickListener() {
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
                        mAD.setText(myDayStart);
                        ArrivalDateTime = (int)milis;
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
        mLBD.setOnClickListener(new View.OnClickListener() {
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
                        mLD.setText(myDayEnd);
                        LeaveDateTime = (int)milis;
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }

        });


        //Lưu ý phần service type: tạo 1 chuỗi trống, mỗi khi click vào button hotel hoặc restaurant thì sẽ gán chuỗi bằng "Hotel" hoặc "Restaurant" tương ứng và cộng chuỗi này vào phần tên Stop point, ví dụ
        // tên người dùng nhập là "ABC" và nhấp vào nút Hotel thì tên hiển thị ra TextView stopPointName  sẽ là "Hotel ABC"

    }

}
