package com.ygaps.travelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.Coordinate;
import com.ygaps.travelapp.data.model.CoordinateSet;
import com.ygaps.travelapp.data.model.ListStopPoint;
import com.ygaps.travelapp.data.model.ListStopPointSetSP;
import com.ygaps.travelapp.data.model.MoreOneCoordinate;
import com.ygaps.travelapp.data.model.OneCoordinate;
import com.ygaps.travelapp.data.model.ServiceDetail;
import com.ygaps.travelapp.data.model.StopPoint;
import com.ygaps.travelapp.data.model.StopPointSetSP;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StopPoints extends FragmentActivity implements OnMapReadyCallback {

    private ArrayList<StopPoint> data = new ArrayList<>();
    private String[] selection = {"Choose", "See Detail"};
    private GoogleMap mMap;
    private FusedLocationProviderClient client;
    private Location currentLocation;
    private ArrayList<StopPointSetSP> listStoppoint = new ArrayList<>();
    private ArrayList<Integer> delIds = new ArrayList<>();
    private String tourId = "";
    private Double lat, lng;
    private LatLng newLatLng;
    private MyApplication app;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_points);
        api = retrofit.getClient().create(API.class);
        app = (MyApplication) getApplication();
        client = LocationServices.getFusedLocationProviderClient(this);
        GetLastLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        ImageButton savedStopPoints = findViewById(R.id.savedStopPoints);
        View.OnClickListener showStopPoints = new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),SavedStopPoints.class);
                intent.putExtra("TourId", tourId);
                startActivity(intent);
            }
        };
        savedStopPoints.setOnClickListener(showStopPoints);

        Intent intent = getIntent();
        if (intent.hasExtra("TourId")){
            tourId = intent.getStringExtra("TourId");
        }else{
            tourId = "nope";
        }

        //tourId = AddTour.tourID;
        ImageButton getBackHome = findViewById(R.id.getBackHome);
        View.OnClickListener getHome = new View.OnClickListener(){
            public void onClick(View v){
                //do something
                Intent retIntent = new Intent();
                setResult(RESULT_OK, retIntent);
                finish();
            }
        };
        getBackHome.setOnClickListener(getHome);

        ImageButton getCurrentLocation = findViewById(R.id.getCurrentLocation);
        View.OnClickListener getCurLocation = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Toast.makeText(getApplicationContext(), "Current Location", Toast.LENGTH_SHORT).show();
                GetLastLocation();
            }
        };
        getCurrentLocation.setOnClickListener(getCurLocation);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                onTouch(latLng);
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showDialog(marker);
                return false;
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                onHold(latLng);
            }
        });
    }

    private void GetLastLocation() {
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
                    currentLocation=location;
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()+" "+currentLocation.getLongitude(),Toast.LENGTH_SHORT)
                            .show();
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(StopPoints.this);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 101:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GetLastLocation();
                }
            }
            break;
        }
    }

    public void onHold(final LatLng latLng) {
        Log.d("StopPoints", "OnHold "+ tourId);
        mMap.clear();
        generateStopPointMarker1(data);
        //show AlertDialog Create a New Stop Point
        //After Create Finish()
        new AlertDialog.Builder(StopPoints.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Create A New Stop Point ?")
                .setPositiveButton("Create A New Stop Point", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(StopPoints.this, CreateStopPoint.class);
                        //set RequestCode or Set noticode.
                        intent.putExtra("NewOrOld", 1);//1 new
                        intent.putExtra("TourIdToAddStopPoint", tourId);
                        intent.putExtra("Address", convertToAddress(latLng.latitude, latLng.longitude));
                        intent.putExtra("Latitude", latLng.latitude);
                        intent.putExtra("Longtitude", latLng.longitude);
                        intent.putExtra("TourId", tourId);
                        Log.d("CreateSP", convertToAddress(latLng.latitude ,latLng.longitude)+" "+latLng);
                        startActivity(intent);
                    }
                })
                .show()
                .getWindow().setGravity(Gravity.BOTTOM);
    }

    public void onTouch(LatLng latLng){
        Log.d("StopPoints", "OnTouch and " + tourId);
        mMap.clear();
        data.clear();

        OneCoordinate oneCoordinate = new OneCoordinate(true,
                new Coordinate(latLng.latitude, latLng.longitude));

        API api = retrofit.getClient().create(API.class);
        Call<ListStopPoint> call = api.oneCoordinate(app.userToken, oneCoordinate);
        call.enqueue(new Callback<ListStopPoint>() {
            @Override
            public void onResponse(Call<ListStopPoint> call, Response<ListStopPoint> response) {
                if(!response.isSuccessful()){
                    Log.d("StopPoints", response.isSuccessful() +"");
                    return;
                }
                Log.d("StopPoints", response.code()+"");
                ListStopPoint res = response.body();
                data = res.getStopPoints();
                Log.d("StopPoints", data.size()+"");
                generateStopPointMarker1(data);
            }

            @Override
            public void onFailure(Call<ListStopPoint> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void generateStopPointMarker1(ArrayList<StopPoint> data)
    {
        for (int i =0 ;i<data.size();i++){
            LatLng tempPos = new LatLng(Double.valueOf(data.get(i).getLat()),Double.valueOf(data.get(i).get_long()));
            MarkerOptions markerOptions = new MarkerOptions().title(data.get(i).getName()).snippet(data.get(i).getId().toString())
                    .position(tempPos);
            switch (data.get(i).getServiceTypeId()+1)
            {
                case 1:
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant));
                    break;
                case 2:
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel));
                    break;
                case 3:
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.reststation));
                    break;
                case 4:
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.other));
                    break;

            }
            mMap.addMarker(markerOptions);
        }
    }
    private void showDialog(final Marker marker){
        new AlertDialog.Builder(StopPoints.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("What Do You Want To Do ?")
                .setItems(selection, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Toast.makeText(getApplicationContext(),"Choose This Stop Point", Toast.LENGTH_SHORT).show();
                                Log.d("StopPoints","choose on marker ID " + marker.getSnippet());
                                Intent intent1 = new Intent(StopPoints.this, CreateStopPoint.class);
                                intent1.putExtra("NewOrOld",0);//0 old
                                intent1.putExtra("TourIdToAddStopPoint", tourId);
                                intent1.putExtra("ServiceId", marker.getSnippet().toString());
                                intent1.putExtra("TourId", tourId);
                                startActivity(intent1);
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(),"See More", Toast.LENGTH_SHORT).show();
                                Intent intent =  new Intent(getApplicationContext(), StopPointInfo_Main.class);
                                if (marker.getSnippet() != ""){
                                    intent.putExtra("StopPointIdForSeeDetail", marker.getSnippet());
                                }
                                intent.putExtra("SeeFrom",1); // see from map is 1, see from List StopPoint in Tour is 0
                                startActivity(intent);
                                break;
                        }

                    }
                }).show()
                .getWindow()
                .setGravity(Gravity.BOTTOM);
    }

    public String convertToAddress(double lat, double lng){
        String strAddr="";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if(addresses!=null){
                Address returnAddr = addresses.get(0);
                StringBuilder strReturnedAddr = new StringBuilder("");
                for (int i = 0 ; i <=returnAddr.getMaxAddressLineIndex();i++){
                    strReturnedAddr.append(returnAddr.getAddressLine(i)).append("\n");
                }
                strAddr = strReturnedAddr.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return strAddr;
    }
    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 111:{

                break;
            }
        }
    }
}
