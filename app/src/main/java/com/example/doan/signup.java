package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doan.ui.login.LoginActivity;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        final TextView loginLink = findViewById(R.id.loginLink);
        View.OnClickListener login = new View.OnClickListener(){
            public void onClick(View v)
            {
                finish();
            }
        };
        loginLink.setOnClickListener(login);


        //Code khi nhan vao nut Sign up
        final Button signUp = findViewById(R.id.signUp);
        View.OnClickListener signUpBtn = new View.OnClickListener(){
            public void onClick(View v)
            {

            }
        };
        signUp.setOnClickListener(signUpBtn);

        //Code khi nhan vao nut sign up bang google
        final ImageButton signUpGoogle = findViewById(R.id.signUpGoogle);
        View.OnClickListener signUpBtnGoogle = new View.OnClickListener(){
            public void onClick(View v)
            {

            }
        };
        signUpGoogle.setOnClickListener(signUpBtnGoogle);

        //Code khi nhap vao nut sign up bang facebook
        final ImageButton signUpFacebook = findViewById(R.id.signUpFacebook);
        View.OnClickListener signUpBtnFacebook = new View.OnClickListener(){
            public void onClick(View v)
            {

            }
        };
        signUpFacebook.setOnClickListener(signUpBtnFacebook);
    }
}
