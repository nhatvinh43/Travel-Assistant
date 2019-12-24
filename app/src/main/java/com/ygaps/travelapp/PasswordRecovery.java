package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ygaps.travelapp.data.model.PasswordRecoveryOtp;
import com.ygaps.travelapp.data.remote.API;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ygaps.travelapp.data.remote.retrofit.getClient;

public class PasswordRecovery extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        ImageButton backBtn = findViewById(R.id.passwordRecoveryBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final EditText email = findViewById(R.id.passwordRecoveryEmail);
        final EditText phone = findViewById(R.id.passwordRecoveryPhone);

        Button submit = findViewById(R.id.passwordRecoverySubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API api = getClient().create(API.class);
                String email_recovery = email.getText().toString();
                String phone_recovery = phone.getText().toString();
                PasswordRecoveryOtp passwordRecoveryOtp;
                final String value;
                if (!email_recovery.equals("") && phone_recovery.equals("")){
                    value = email_recovery;
                    passwordRecoveryOtp = new PasswordRecoveryOtp("email",email_recovery);
                }
                else if (email_recovery.equals("") && !phone_recovery.equals("")){
                    passwordRecoveryOtp = new PasswordRecoveryOtp("phone",phone_recovery);
                    value = phone_recovery;
                }else{
                    return;
                }

                Call<JsonObject> call = api.requestPasswordRecoveryOtp(passwordRecoveryOtp);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            JsonObject error =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                            Toast.makeText(PasswordRecovery.this,error.get("message").getAsString(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        JsonObject loginResponse = response.body();
                        Integer userId = loginResponse.get("userId").getAsInt();
                        Intent intent = new Intent(context, PasswordRecovery_OTP.class);
                        intent.putExtra("userId",userId);
                        intent.putExtra("type",loginResponse.get("type").getAsString());
                        intent.putExtra("value",value);
                        startActivity(intent);
                        finish();
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
