package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doan.data.model.PasswordRecoveryOtp;
import com.example.doan.data.model.VertifyPasswordRecoveryOtp;
import com.example.doan.data.remote.API;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.doan.data.remote.retrofit.getClient;

public class PasswordRecovery_OTP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery__otp);
        ImageButton backBtn = findViewById(R.id.passwordRecoveryOTPBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Intent intent = getIntent();
        final Integer userId = intent.getIntExtra("userId",0);

        Button submit = findViewById(R.id.passwordRecoveryOTPSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText otp = findViewById(R.id.passwordRecoveryOTP);
                EditText newpassword = findViewById(R.id.passwordRecoveryNewPassword);
                EditText newpasswordconfirm = findViewById(R.id.passwordRecoveryNewPasswordConfirm);

                if (!((newpassword.getText().toString())
                        .equals(newpasswordconfirm.getText().toString()))){
                    Toast.makeText(PasswordRecovery_OTP.this,
                            "New password is not the same as new password confirm", Toast.LENGTH_SHORT).show();
                    return;
                }
                API api = getClient().create(API.class);

                VertifyPasswordRecoveryOtp vertifyPasswordRecoveryOtp =
                        new VertifyPasswordRecoveryOtp(userId,newpassword.getText().toString(),
                                otp.getText().toString());
                Call<JsonObject> call = api.verifyPasswordRecoveryOtp(vertifyPasswordRecoveryOtp);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            JsonObject error =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                            Toast.makeText(PasswordRecovery_OTP.this,error.get("message").getAsString(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        JsonObject passwordRecoveryResponse = response.body();
                        Toast.makeText(PasswordRecovery_OTP.this,
                                passwordRecoveryResponse.get("message").toString(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("ErrorPasswordRecovery",t.getMessage());
                    }
                });
            }
        });

        Button OTPResend = findViewById(R.id.passwordRecoveryOTPResend);
        OTPResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API api = getClient().create(API.class);
                Log.d("TypeValue",intent.getStringExtra("type") + ": " + intent.getStringExtra("value"));
                PasswordRecoveryOtp passwordRecoveryOtp = new PasswordRecoveryOtp(
                        intent.getStringExtra("type"),intent.getStringExtra("value")
                );
                Call<JsonObject> call = api.requestPasswordRecoveryOtp(passwordRecoveryOtp);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            JsonObject error =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                            Toast.makeText(PasswordRecovery_OTP.this,error.get("message").getAsString(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(PasswordRecovery_OTP.this,"successfull" , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("ErrorPasswordRecovery",t.getMessage());
                    }
                });
            }
        });
    }

}
