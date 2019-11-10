package com.example.doan.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.signup;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.usernameLogin);
        final EditText passwordEditText = findViewById(R.id.passwordLogin);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final TextView signUpLink = findViewById(R.id.loginLink);

        final Intent intent = new Intent(this, signup.class);
        View.OnClickListener signUp = new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);

            }
        };
        signUpLink.setOnClickListener(signUp);

        //Code khi nhan vao nut Login
        final Button login = findViewById(R.id.login);
        View.OnClickListener loginBtn = new View.OnClickListener(){
            public void onClick(View v)
            {

            }
        };
        login.setOnClickListener(loginBtn);

        //Code khi nhan vao nut login bang google
        final ImageButton loginGoogle = findViewById(R.id.loginGoogle);
        View.OnClickListener loginBtnGoogle = new View.OnClickListener(){
            public void onClick(View v)
            {

            }
        };
        loginGoogle.setOnClickListener(loginBtnGoogle);

        //Code khi nhap vao nut login bang facebook
        final ImageButton loginFacebook = findViewById(R.id.loginFacebook);
        View.OnClickListener loginBtnFacebook = new View.OnClickListener(){
            public void onClick(View v)
            {

            }
        };
        loginFacebook.setOnClickListener(loginBtnFacebook);
    }

}
