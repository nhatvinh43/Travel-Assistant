package com.ygaps.travelapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ygaps.travelapp.data.model.EditUserInfo;
import com.ygaps.travelapp.data.remote.API;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ygaps.travelapp.data.remote.retrofit.getClient;

public class Settings_EditInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__edit_info);

        final TextView userName = findViewById(R.id.settingsUsername);
        final EditText fullName = findViewById(R.id.settingsEditInfoFullName);
        final EditText editEmail = findViewById(R.id.settingsEditInfoEmail);
        final EditText editPhone = findViewById(R.id.settingsEditInfoPhone);

        API api = getClient().create(API.class);
        Call<JsonObject> call = api.userInfo(MainActivity.app.userToken);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                    Toast.makeText(Settings_EditInfo.this,errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                    return;
                }
                JsonObject loginResponse = response.body();

                String name = loginResponse.get("fullName").getAsString();
                String email = loginResponse.get("email").getAsString();
                String phone = loginResponse.get("phone").getAsString();

                userName.setText(name);
                fullName.setText(name);
                editEmail.setText(email);
                editPhone.setText(phone);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("ErrorOnSetting",t.getMessage());
            }
        });

        ImageButton Confirm = findViewById(R.id.settingsEditInfoConfirm);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API api = getClient().create(API.class);
                //String fullName, String email, String phone, Integer gender, String dob)
                EditUserInfo editUserInfo = new EditUserInfo(fullName.getText().toString(),
                        editEmail.getText().toString(),
                        editPhone.getText().toString()
                        ,0,null);

                Call<JsonObject> call1 = api.updateUserInfo(((MyApplication)getApplication()).userToken,editUserInfo);
                call1.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                            Toast.makeText(Settings_EditInfo.this,errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        JsonObject loginResponse = response.body();
                        Toast.makeText(Settings_EditInfo.this, loginResponse.get("message").toString(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("ErrorOnSettingEditInfo",t.getMessage());
                    }
                });
            }
        });

        ImageButton back = findViewById(R.id.settingsEditInfoBack);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
