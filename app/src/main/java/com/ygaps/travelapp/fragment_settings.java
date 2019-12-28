package com.ygaps.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ygaps.travelapp.data.remote.retrofit.getClient;

public class fragment_settings extends Fragment {
    private MyApplication app;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_navigation_settings, container, false);

        app = (MyApplication)getActivity().getApplication();
        Log.d("UserTokenSetting",app.userToken);
        API api = getClient().create(API.class);
        Call<JsonObject> call = api.userInfo(app.userToken);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                    Toast.makeText(getContext(),errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                    return;
                }
                JsonObject loginResponse = response.body();
                String name;
                if ((loginResponse.get("fullName")).isJsonNull()){
                    name = "No Name";
                }else{
                    name = loginResponse.get("fullName").getAsString();
                }

                TextView userName = view.findViewById(R.id.settingsUsername);
                userName.setText(name+"|"+app.userId);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("ErrorOnSetting",t.getMessage());
            }
        });

        final Button editInfo = view.findViewById(R.id.settingsEditInfoButton);
        editInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),Settings_EditInfo.class);
                startActivityForResult(intent,111);
            }
        });

        Button logOut = view.findViewById(R.id.settingsSignOutButton);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                SharedPreferences sharedPreferences =getActivity().getSharedPreferences("DoAn", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==111){
            if (resultCode==getActivity().RESULT_OK){
                getActivity().recreate();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
