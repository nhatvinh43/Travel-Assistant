package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.data.model.RegisterResponse;
import com.example.doan.data.remote.API;
import com.example.doan.ui.login.LoginActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.doan.data.remote.retrofit.getClient;
import static com.example.doan.data.remote.retrofit.retrofit;

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

        final TextView phoneSignUp = findViewById(R.id.phoneSignUp);
        final TextView emailSignUp = findViewById(R.id.emailSignUp);
        final TextView passwordSignUp = findViewById(R.id.passwordSignUp);
        final TextView passwordConfirmSignUp = findViewById(R.id.passwordConfirmSignUp);

        //Code khi nhan vao nut Sign up
        final Button signUp = findViewById(R.id.signUp);
        View.OnClickListener signUpBtn = new View.OnClickListener(){
            public void onClick(View v)
            {
                if (! passwordSignUp.getText().toString().equals(passwordConfirmSignUp.getText().toString())){
                    Toast.makeText(signup.this,
                            "Password confirm is not the same as Password",Toast.LENGTH_LONG).show();
                    return;
                }

                API api = getClient().create(API.class);

                Call<RegisterResponse> call = api.register(passwordSignUp.getText().toString(),
                        null, emailSignUp.getText().toString(),
                        phoneSignUp.getText().toString(),null,null,1);

                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call,
                                           Response<RegisterResponse> response) {
                        if (!response.isSuccessful()) {
                            try{
                                Toast.makeText(signup.this, response.errorBody().string(),Toast.LENGTH_SHORT).show();
                            } catch (IOException e){
                                Toast.makeText(signup.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            return;
                        }

                        RegisterResponse registerResponse = response.body();

                        Toast.makeText(signup.this,
                                "Sign up successfully. Please sign in",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(signup.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(signup.this, t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
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
