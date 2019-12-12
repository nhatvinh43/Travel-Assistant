package com.example.doan.ui.login;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.AddTour;
import com.example.doan.MainActivity;
import com.example.doan.R;
import com.example.doan.data.model.LoginData;
import com.example.doan.data.remote.API;
import com.example.doan.signup;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.doan.data.remote.retrofit.getClient;
import static com.example.doan.data.remote.retrofit.retrofit;


public class LoginActivity extends AppCompatActivity {
    public static String TOKEN;
    public static String USERID;
    private LoginViewModel loginViewModel;
    private CallbackManager callbackManager;

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

                LoginData loginData = new LoginData(emailEditText.getText().toString(),passwordEditText.getText().toString());

                Call<JsonObject> call = api.getLogin(loginData);
                
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("LOGIN CODE", response.code()+"");
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                            Toast.makeText(LoginActivity.this,errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        JsonObject loginResponse = response.body();

                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                        TOKEN = loginResponse.get("token").getAsString();
                        USERID = loginResponse.get("userId").getAsString();
                        intent1.putExtra("userId",USERID);
                        intent1.putExtra("token",TOKEN);
                        startActivity(intent1);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
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
        final LoginButton loginFacebook = findViewById(R.id.loginFacebook);

        callbackManager = CallbackManager.Factory.create();
        loginFacebook.setReadPermissions("public_profile");
        loginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                API api = getClient().create(API.class);
                Call<JsonObject> call = api.loginFacebook(accessToken.getToken());

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                            Toast.makeText(LoginActivity.this,errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        JsonObject loginResponse = response.body();

                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                        intent1.putExtra("userId",loginResponse.get("userId").getAsString());
                        intent1.putExtra("token",loginResponse.get("token").getAsString());
                        TOKEN = loginResponse.get("token").getAsString();
                        startActivity(intent1);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "login error", Toast.LENGTH_SHORT).show();
                try {
                    PackageInfo info = getPackageManager().getPackageInfo(
                            "com.example.doan",
                            PackageManager.GET_SIGNATURES);
                    for (Signature signature : info.signatures) {
                        MessageDigest md = MessageDigest.getInstance("SHA");
                        md.update(signature.toByteArray());
                        Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                    }
                }
                catch (PackageManager.NameNotFoundException e) {
                    Log.d("FacebookLogin: ",e.getMessage());
                }
                catch (NoSuchAlgorithmException e) {
                    Log.d("FacebookLogin: ",e.getMessage());
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
