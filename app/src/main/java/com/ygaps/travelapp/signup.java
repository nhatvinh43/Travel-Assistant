package com.ygaps.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.remote.API;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ygaps.travelapp.data.remote.retrofit.getClient;

public class signup extends AppCompatActivity {
    private CallbackManager callbackManager;
    private MyApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        app = (MyApplication)getApplication();

        final TextView loginLink = findViewById(R.id.loginLink);
        View.OnClickListener login = new View.OnClickListener(){
            public void onClick(View v)
            {
                finish();
            }
        };
        loginLink.setOnClickListener(login);

        final ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        final TextView phoneSignUp = findViewById(R.id.phoneSignUp);
        final TextView emailSignUp = findViewById(R.id.emailSignUp);
        final TextView passwordSignUp = findViewById(R.id.passwordSignUp);
        final TextView passwordConfirmSignUp = findViewById(R.id.passwordConfirmSignUp);

        //Code khi nhan vao nut Sign up
        final Button signUp = findViewById(R.id.signUp);
        View.OnClickListener signUpBtn = new View.OnClickListener(){
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
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

                        progressBar.setVisibility(View.GONE);
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
        //final ImageButton signUpFacebook = findViewById(R.id.signUpFacebook);
        //View.OnClickListener signUpBtnFacebook = new View.OnClickListener(){
        //    public void onClick(View v)
        //    {

        //    }
        //};
        //signUpFacebook.setOnClickListener(signUpBtnFacebook);


        final LoginButton loginFacebook = findViewById(R.id.signUpFacebook);

        callbackManager = CallbackManager.Factory.create();
        loginFacebook.setReadPermissions("public_profile");
        loginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Log.d("accessTokenFacebook",accessToken.getToken());
                API api = getClient().create(API.class);
                Call<JsonObject> call = api.loginFacebook(accessToken.getToken());

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                            Toast.makeText(signup.this,errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        JsonObject loginResponse = response.body();

                        Intent intent1 = new Intent(signup.this, MainActivity.class);
                        app.userToken = loginResponse.get("token").getAsString();
                        app.userId = loginResponse.get("userId").getAsString();
                        startActivity(intent1);

                        //share preferences save User Token
                        SharedPreferences sharedPreferences = getSharedPreferences("DoAn", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.putString("UserToken",app.userToken);
                        editor.putString("UserId",app.userId);
                        editor.apply();

                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(signup.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(signup.this, "login error", Toast.LENGTH_SHORT).show();
                try {
                    PackageInfo info = getPackageManager().getPackageInfo(
                            "com.ygaps.travelapp",
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
