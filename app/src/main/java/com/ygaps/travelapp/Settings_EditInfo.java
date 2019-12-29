package com.ygaps.travelapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.EditUserInfo;
import com.ygaps.travelapp.data.remote.API;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ygaps.travelapp.data.remote.retrofit.getClient;

public class Settings_EditInfo extends AppCompatActivity {
    private String Dob;
    private String CurDob;
    private int CurDay;
    private int CurMonth;
    private int CurYear;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__edit_info);

        final TextView userName = findViewById(R.id.settingsUsername);
        final EditText fullName = findViewById(R.id.settingsEditInfoFullName);
        final EditText editEmail = findViewById(R.id.settingsEditInfoEmail);
        final EditText editPhone = findViewById(R.id.settingsEditInfoPhone);
        final TextView editDob = findViewById(R.id.settingsEditInfoDOB);
        final ImageButton btnEditDob = findViewById(R.id.settingsInfoEditDOBButton);
        final androidx.appcompat.widget.AppCompatSpinner gender = findViewById(R.id.settingsEditInfoGender);

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
                int genderselect = 0;
                if (!(loginResponse.get("gender").isJsonNull())){
                    int CurGender = loginResponse.get("gender").getAsInt();
                    if (CurGender == 0){
                        genderselect = 1;
                    }else{
                        genderselect = 0;
                    }
                    Log.d("GenderSelect","Current gender  " + genderselect);
                }
                String name = "";
                if (!(loginResponse.get("fullName").isJsonNull())){
                    name = loginResponse.get("fullName").getAsString();
                }
                String email = "";
                if (!(loginResponse.get("email").isJsonNull())){
                    email = loginResponse.get("email").getAsString();
                }
                String phone = "";
                if (!(loginResponse.get("phone").isJsonNull())) {
                    phone = loginResponse.get("phone").getAsString();
                }
                if (!(loginResponse.get("dob").isJsonNull())){
                    CurDob = loginResponse.get("dob").getAsString();
                    CurYear = Integer.parseInt(CurDob.substring(0,4));
                    CurMonth = Integer.parseInt(CurDob.substring(5,7));
                    CurDay = Integer.parseInt(CurDob.substring(8,10));
                    CurDob = CurDay + "/" + CurMonth + "/" + CurYear;
                    Dob = CurYear + "-" + CurMonth + "-" + CurDay;
                    Log.d("EditUserInfo", CurDay + CurMonth + CurYear + "");
                }
                gender.setSelection(genderselect);
                userName.setText(name);
                fullName.setText(name);
                editEmail.setText(email);
                editPhone.setText(phone);
                editDob.setText(CurDob);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("ErrorOnSetting",t.getMessage());
            }
        });

        btnEditDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear;
                int mMonth;
                int mDay;
                final DatePickerDialog dpd;
                if (CurDob == null){
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                }else {
                    mYear = CurYear;
                    mMonth = CurMonth;
                    mDay = CurDay;
                }

                dpd = new DatePickerDialog(Settings_EditInfo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Dob = year + "-" + (month+1) + "-" + dayOfMonth;
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        String TextDob = dayOfMonth +"/"+(month+1)+"/"+year;
                        CurDay = dayOfMonth;
                        CurMonth = month + 1;
                        CurYear = year;
                        editDob.setText(TextDob);
                    }
                }, mYear, mMonth-1, mDay);
                dpd.show();
            }
        });

        ImageButton Confirm = findViewById(R.id.settingsEditInfoConfirm);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                API api = getClient().create(API.class);

                int genderSelected = 0;
                if (gender.getSelectedItem().toString().equals("Male")){
                    genderSelected = 1;
                }
                Log.d("GenderSelect","new select " + genderSelected);
                //String fullName, String email, String phone, Integer gender, String dob)
                EditUserInfo editUserInfo = new EditUserInfo(fullName.getText().toString(),
                        editEmail.getText().toString(),editPhone.getText().toString(),genderSelected,Dob);

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

                Intent ret = new Intent();
                ret.putExtra("fullname",fullName.getText().toString());
                setResult(RESULT_OK, ret);
                finish();
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
