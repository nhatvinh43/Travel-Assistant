package com.ygaps.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.Manifest;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.CustomAdapter;
import com.ygaps.travelapp.data.model.Notification;
import com.ygaps.travelapp.data.model.NotificationOnRoad;
import com.ygaps.travelapp.data.model.StopPointTourInfo;
import com.ygaps.travelapp.data.model.TourInfo;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourInfo_MapScreen extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient client;
    private MediaRecorder recorder ;
    private String outputFile;
    private int currentFormat = 0;
    private Location currentLocation;
    private Dialog eventsDialog;
    private int selectedNotificationType =0;
    private SupportMapFragment supportMapFragment;
    private MyApplication app;
    private Marker des;
    private API api = retrofit.getClient().create(API.class);
    private ArrayList<StopPointTourInfo> listStopPoint = new ArrayList<>();
    private Handler handler;
    private Runnable runnable;

    private EditText notificationMsg;
    private EditText notificationVelocity;
    private TextView selectedNotification;
    private ImageButton policeStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info__map_screen);
        final ImageButton record = findViewById(R.id.tourInfoMapSendVoiceRecorderButton);
        app = (MyApplication) getApplication();
        client = LocationServices.getFusedLocationProviderClient(this);
        GetLastLocation();


        final ImageButton currentLocation = findViewById(R.id.tourInfoMapCurrentLocationButton);
        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"GetCurrentLocation", Toast.LENGTH_LONG).show();
                GetLastLocation();
            }
        });
        ImageButton sendEvents = findViewById(R.id.tourInfoMapSendNotificationsButton);
        sendEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsDialog = new Dialog(TourInfo_MapScreen.this);
                eventsDialog.setContentView(R.layout.dialog_notifications);
                eventsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(eventsDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                eventsDialog.show();
                eventsDialog.getWindow().setAttributes(lp);


                notificationMsg = eventsDialog.findViewById(R.id.tourInfoDialogMsg);
                notificationVelocity = eventsDialog.findViewById(R.id.tourInfoDialogVelocity);
                selectedNotification = eventsDialog.findViewById(R.id.tourInfoDialogSelectedNotification);
                policeStation = eventsDialog.findViewById(R.id.tourInfoDialogPolice);


                policeStation.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        selectedNotificationType=1;
                        selectedNotification.setText(R.string.policeStation);
                        selectedNotification.setVisibility(View.VISIBLE);
                        notificationMsg.setVisibility(View.GONE);
                        notificationVelocity.setVisibility(View.GONE);
                    }
                });
                ImageButton problemsOnRoad = eventsDialog.findViewById(R.id.tourInfoDialogProblems);
                problemsOnRoad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedNotificationType=2;
                        selectedNotification.setText(R.string.problemsOnRoad);
                        selectedNotification.setVisibility(View.VISIBLE);
                        notificationMsg.setVisibility(View.VISIBLE);
                        notificationVelocity.setVisibility(View.GONE);
                    }
                });

                ImageButton speedLimit = eventsDialog.findViewById(R.id.tourInfoDialogSpeedLimit);
                speedLimit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedNotificationType=3;
                        selectedNotification.setText(R.string.speedLimitSign);
                        selectedNotification.setVisibility(View.VISIBLE);
                        notificationMsg.setVisibility(View.GONE);
                        notificationVelocity.setVisibility(View.VISIBLE);
                    }
                });

            }
        });

        //map
    }
    class MyRunable implements Runnable{
        @Override
        public void run() {

        }
    }

    private Boolean checkPermissionFromDevice(){
        int writeExternalStorageResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recordAudioResult = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return (writeExternalStorageResult == PackageManager.PERMISSION_GRANTED &&
                recordAudioResult == PackageManager.PERMISSION_GRANTED);
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },111);
    }

    private void setupMediaRecoder(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(outputFile);
    }
    private void startRecording(){
        try{
            recorder.prepare();
        }catch (IOException e){

        }
        recorder.start();
    }

    private void stopRecording(){
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void playRecording(){
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(outputFile);
            mediaPlayer.prepare();
        }catch (IOException e){

        }
        Toast.makeText(getApplicationContext(),"Play...", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

        DrawAllStopPoints();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                onTouch(latLng);
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ShowDialog(marker);
                return false;
            }
        });

        switch (selectedNotificationType){
            case 1:
                NotificationOnRoad notificationOnRoad = new NotificationOnRoad(currentLocation.getLatitude(), currentLocation.getLongitude(),
                        Integer.valueOf(TourInfo_Main.tourId), Integer.valueOf(app.userId), 1, 0, "");
                Call<JsonObject>call = api.sendNotificationOnRoad(app.userToken, notificationOnRoad);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("SendNoti", response.code()+"");
                        if (!response.isSuccessful()){
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
                break;
            case 2:
                NotificationOnRoad notificationOnRoad1 = new NotificationOnRoad(currentLocation.getLatitude(), currentLocation.getLongitude(),
                        Integer.valueOf(TourInfo_Main.tourId), Integer.valueOf(app.userId), 2, 0,"");
                Call<JsonObject>call1 = api.sendNotificationOnRoad(app.userToken, notificationOnRoad1);
                call1.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("SendNoti", response.code()+"");
                        if (!response.isSuccessful()){
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
                break;
            case 3:
                NotificationOnRoad notificationOnRoad2 = new NotificationOnRoad(currentLocation.getLatitude(), currentLocation.getLongitude(),
                        Integer.valueOf(TourInfo_Main.tourId), Integer.valueOf(app.userId), 3,
                        0 ,"");
                Call<JsonObject>call2 = api.sendNotificationOnRoad(app.userToken, notificationOnRoad2);
                call2.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("SendNoti", response.code()+"");
                        if (!response.isSuccessful()){
                            return;
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
                break;
        }
    }

    private void ShowDialog(final Marker marker){
        new AlertDialog.Builder(TourInfo_MapScreen.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Go There?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DrawLine(marker);
                    }
                }).show()
                .getWindow()
                .setGravity(Gravity.BOTTOM);
    }
    private void DrawLine(final Marker marker){
        des = marker;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                mMap.clear();
                DrawAllStopPoints();
                GetLastLocation();
                Polyline polyline = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), des.getPosition())
                        .width(10)
                        .color(Color.BLUE));
                handler.postDelayed(this,5000);
            }
        };
        handler.postDelayed(runnable, 1000);


    }
    private void onTouch(LatLng latLng){
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }
    private void DrawAllStopPoints(){
        Call<TourInfo> call = api.getTourInfoTV(app.userToken, Integer.valueOf(TourInfo_Main.tourId));
        call.enqueue(new Callback<TourInfo>() {
            @Override
            public void onResponse(Call<TourInfo> call, Response<TourInfo> response) {
                Log.d("MapScreen", response.code()+"");
                if (!response.isSuccessful()){
                    return;
                }
                TourInfo tourInfo = response.body();
                listStopPoint = tourInfo.getStopPoints();
                for (int i =0 ; i<listStopPoint.size();i++){
                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(Double.valueOf(listStopPoint.get(i).getLat()),
                            Double.valueOf(listStopPoint.get(i).getLong()))).title(listStopPoint.get(i).getName());
                    mMap.addMarker(markerOptions);
                }
            }

            @Override
            public void onFailure(Call<TourInfo> call, Throwable t) {

            }
        });
    }
    private void GetLastLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
           @Override
           public void onSuccess(Location location) {
               if (location!=null){
                   currentLocation = location;
                   SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                           .findFragmentById(R.id.tourInfoMap);
                   mapFragment.getMapAsync(TourInfo_MapScreen.this);
               }
           }
       });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 101:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GetLastLocation();
                }
                break;
            }
            case 111:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Permission Dinied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
