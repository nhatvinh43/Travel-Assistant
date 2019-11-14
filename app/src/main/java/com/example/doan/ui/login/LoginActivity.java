package com.example.doan.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.MainActivity;
import com.example.doan.R;
import com.example.doan.data.model.LoginResponse;
import com.example.doan.data.remote.API;
import com.example.doan.signup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.doan.data.remote.retrofit.getClient;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText emailEditText = findViewById(R.id.emailLogin);
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
                API api = getClient().create(API.class);

                Call<LoginResponse> call = api.login(emailEditText.getText().toString(),passwordEditText.getText().toString());

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            if (response.code()==400){
                                Toast.makeText(LoginActivity.this,"Missing email/password",Toast.LENGTH_LONG).show();
                            }else if(response.code()==404){
                                Toast.makeText(LoginActivity.this,"Wrong email/password",Toast.LENGTH_LONG).show();
                            }
                            return;
                        }
                        LoginResponse loginResponse = response.body();

                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                        intent1.putExtra("userId",loginResponse.getUserId());
                        intent1.putExtra("token",loginResponse.getToken());
                        startActivity(intent1);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
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
