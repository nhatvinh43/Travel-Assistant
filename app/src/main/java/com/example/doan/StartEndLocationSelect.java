package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doan.data.model.ListStopPoint;
import com.example.doan.data.model.StopPoint;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;
import com.example.doan.ui.login.LoginActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartEndLocationSelect extends FragmentActivity implements OnMapReadyCallback {

    private String[] typeServiceID = {"Restaurant", "Hotel", "Rest Station", "Other"};
    private static final int REQUESTCODE = 101;
    private static final int REQUSETCODE1 = 111;
    private GoogleMap mMap;
    private FusedLocationProviderClient client;
    private Location currentLocation;
    private double lat, lng;
    private LatLng newLatLng;
    private ImageButton mCancel, mConfirm, mGetHome;
    private boolean flag = false;

    //test data
    LatLng latlng1 = new LatLng(10.459765,106.721583);
    LatLng latlng2 = new LatLng(10.463614,106.720246);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_end_location_select);

        client = LocationServices.getFusedLocationProviderClient(this);
        GetLastLocation();

        mConfirm = findViewById(R.id.confirmStartEnd);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lat!=0){
                    flag = true;
                }
                if (flag) {
                    Intent intent = new Intent();
                    intent.putExtra("LAT", lat);
                    intent.putExtra("LONG", lng);
                    intent.putExtra("ADDRESSLOCATION", convertToAddress(lat, lng));
                    setResult(RESULT_OK, intent);
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(),"Please Choose a Stop Point",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mGetHome = findViewById(R.id.getCurrentLocationStartEnd);
        mGetHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLastLocation();
            }
        });


        mCancel = findViewById(R.id.cancelStartEnd);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        //MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I AM HERE");

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
        //mMap.addMarker(markerOptions);
        MarkerOptions markerOptions1 = new MarkerOptions().position(latlng1).title("CHO RG");
        MarkerOptions markerOptions2 = new MarkerOptions().position(latlng2).title("AO DINH");
        mMap.addMarker(markerOptions1);
        mMap.addMarker(markerOptions2);
        onTouch();
        onHold();
    }
    private void GetLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUESTCODE);
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    currentLocation=location;
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()+" "+currentLocation.getLongitude(),Toast.LENGTH_SHORT)
                            .show();
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.mapStartEnd);
                    mapFragment.getMapAsync(StartEndLocationSelect.this);
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUESTCODE:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GetLastLocation();
                }
            }
            break;
        }
    }
    public void onHold() {
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(getApplicationContext(), "HOLD", Toast.LENGTH_SHORT).show();
                newLatLng = new LatLng(latLng.latitude, latLng.longitude);
                MarkerOptions markerOptions = new MarkerOptions().position(newLatLng).title("New Stop Point");
                mMap.addMarker(markerOptions);
                Intent intent = new Intent(getApplicationContext(),CreateStopPoint.class);
                startActivityForResult(intent, 121);
            }
        });
    }
    public LatLng onTouch(){
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                API api = retrofit.getClient().create(API.class);
                ArrayList<LatLng> tempList = new ArrayList<>();
                tempList.add(latLng);
                Call<ListStopPoint> call = api.getSuggestDes(LoginActivity.TOKEN,true,tempList);
                call.enqueue(new Callback<ListStopPoint>() {
                    @Override
                    public void onResponse(Call<ListStopPoint> call, Response<ListStopPoint> response) {
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                            Toast.makeText(StartEndLocationSelect.this ,errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        ListStopPoint resource = response.body();
                        ArrayList<StopPoint> data = resource.getStopPoints();
                        for (StopPoint spp : data){
                            LatLng tempLatLng = new LatLng(spp.getLat(),spp.getLong());
                            String tempTitle = typeServiceID[spp.getServiceTypeId()] + spp.getName();
                            MarkerOptions markerOptions = new MarkerOptions().position(tempLatLng).title(tempTitle);
                            mMap.addMarker(markerOptions);
                        }
                    }

                    @Override
                    public void onFailure(Call<ListStopPoint> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                newLatLng = new LatLng(latLng.latitude,latLng.longitude);
                lat = latLng.latitude;
                lng = latLng.longitude;
                mMap.animateCamera(CameraUpdateFactory.newLatLng(newLatLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng,20));

                Toast.makeText(getApplicationContext(),convertToAddress(lat,lng),Toast.LENGTH_SHORT).show();
            }
        });
        return newLatLng;
    }
    public String convertToAddress(double lat, double lng){
        String strAddr="";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if(addresses!=null){
                Address returmAddr = addresses.get(0);
                StringBuilder strReturnedAddr = new StringBuilder("");
                for (int i = 0 ; i <=returmAddr.getMaxAddressLineIndex();i++){
                    strReturnedAddr.append(returmAddr.getAddressLine(i)).append("\n");
                }
                strAddr = strReturnedAddr.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return strAddr;
    }

}
