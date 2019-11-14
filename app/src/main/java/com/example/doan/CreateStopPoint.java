package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CreateStopPoint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stop_point);

        //Lưu ý phần service type: tạo 1 chuỗi trống, mỗi khi click vào button hotel hoặc restaurant thì sẽ gán chuỗi bằng "Hotel" hoặc "Restaurant" tương ứng và cộng chuỗi này vào phần tên Stop point, ví dụ
        // tên người dùng nhập là "ABC" và nhấp vào nút Hotel thì tên hiển thị ra TextView stopPointName  sẽ là "Hotel ABC"
    }
}
