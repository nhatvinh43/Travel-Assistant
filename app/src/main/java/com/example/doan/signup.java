package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.data.remote.API;
import com.example.doan.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.doan.data.remote.retrofit.getClient;

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

                Call<JsonObject> call = api.register(passwordSignUp.getText().toString(),
                        null, emailSignUp.getText().toString(),
                        phoneSignUp.getText().toString(),null,null,1);

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call,
                                           Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            JsonObject errorSignup =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                            String ErrorContent = "";
                            int total_err = errorSignup.get("error").getAsInt();
                            JsonArray err_array = errorSignup.get("message").getAsJsonArray();
                            for (int i = 0;i < total_err - 1;i++){
                                JsonObject err = err_array.get(i).getAsJsonObject();
                                ErrorContent += err.get("msg").getAsString() + "\n";
                            }
                            JsonObject err = err_array.get(total_err - 1).getAsJsonObject();
                            ErrorContent += err.get("msg").getAsString();

                            Toast.makeText(signup.this,ErrorContent,Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(signup.this,
                                "Sign up successfully. Please sign in",Toast.LENGTH_LONG).show();

                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
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
