package com.ygaps.travelapp;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.ygaps.travelapp.data.model.Coordinate;
import com.ygaps.travelapp.data.model.CoordinateSet;
import com.ygaps.travelapp.data.model.ListStopPoint;
import com.ygaps.travelapp.data.model.MoreOneCoordinate;
import com.ygaps.travelapp.data.model.OneCoordinate;
import com.ygaps.travelapp.data.model.StopPoint;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//o man hinh di chuyen, can phai them setonmylocationchangelistener

//in this act, when chose a stoppoint then finish.
//In market, set Snippet is id of service/ stoppiont
public class StartEndLocationSelect extends FragmentActivity implements OnMapReadyCallback {

    private ArrayList<StopPoint> data = new ArrayList<>();
    public static String[] typeServiceID = {"Restaurant", "Hotel", "Rest Station", "Other"};
    private String[] selection = {"Choose", "See Detail"};
    private GoogleMap mMap;
    private FusedLocationProviderClient client;
    private Location currentLocation;
    private double lat, lng;
    private LatLng newLatLng;
    private ImageButton mCancel, mConfirm, mGetHome;
    private boolean flag = false;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_end_location_select);
        app = (MyApplication) getApplication();
        client = LocationServices.getFusedLocationProviderClient(this);
        GetLastLocation();

        mConfirm = findViewById(R.id.confirmStartEnd);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lat!=0 || lng != 0){
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

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());

        LatLng HCMC = new LatLng(10.775801, 106.693466);

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                newLatLng = new LatLng(latLng.latitude, latLng.longitude);
                lat = latLng.latitude;
                lng = latLng.longitude;
                mMap.animateCamera(CameraUpdateFactory.newLatLng(newLatLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng,15));
            }
        });
//        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//            @Override
//            public void onMapLongClick(LatLng latLng) {
//                onHold(latLng);
//            }
//        });
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                lat = marker.getPosition().latitude;
//                lng = marker.getPosition().longitude;
//                showDialog(marker);
//                return false;
//            }
//        });
    }

    public void GetLastLocation() {
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
            case 101:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GetLastLocation();
                }
            }
            break;
        }
    }
    public void onHold(LatLng latLng) {
        mMap.clear();
        generateStopPointMarker1(data);

        //show AlertDialog Create a New Stop Point

        //After Create Finish()
        new AlertDialog.Builder(StartEndLocationSelect.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Create A New Stop Point ?")
                .setPositiveButton("Create A New Stop Point", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(StartEndLocationSelect.this, CreateStopPoint.class);
                        startActivity(intent);
                    }
                })
                .show()
                .getWindow().setGravity(Gravity.BOTTOM);
    }

    public void onTouch(LatLng onTouchLatLng){
        Log.d("StartEnd", "Address Here: " + convertToAddress(onTouchLatLng.latitude, onTouchLatLng.longitude));
        mMap.clear();
        data.clear();
        //show AlertDialog to Notify See Detail or Choose.

        //get Bounds
        LatLngBounds latLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        LatLng nearLeft, nearRight, farLeft, farRight;
        nearLeft = mMap.getProjection().getVisibleRegion().nearLeft;
        nearRight = mMap.getProjection().getVisibleRegion().nearRight;
        farLeft = mMap.getProjection().getVisibleRegion().farLeft;
        farRight = mMap.getProjection().getVisibleRegion().farRight;

        ArrayList<Coordinate> far = new ArrayList<>();
        far.add(new Coordinate(farLeft.latitude, farLeft.longitude));
        far.add(new Coordinate(farRight.latitude, farRight.longitude));

        ArrayList<Coordinate> near = new ArrayList<>();
        near.add(new Coordinate(nearLeft.latitude, nearLeft.longitude));
        near.add(new Coordinate(nearRight.latitude, nearRight.longitude));

        CoordinateSet coordinateSetFar = new CoordinateSet(far);
        CoordinateSet coordinateSetNear = new CoordinateSet(near);

        ArrayList<CoordinateSet> coordList = new ArrayList<>();
        coordList.add(coordinateSetFar);
        coordList.add(coordinateSetNear);

        MoreOneCoordinate moreOneCoordinate = new MoreOneCoordinate(false, coordList);

        OneCoordinate oneCoordinate = new OneCoordinate(true,
                new Coordinate(onTouchLatLng.latitude,onTouchLatLng.longitude));

        API api = retrofit.getClient().create(API.class);
        Call<ListStopPoint> call = api.oneCoordinate(app.userToken, oneCoordinate);

        //Call<ListStopPoint> call2 = api.moreCoordinate(LoginActivity.TOKEN, moreOneCoordinate);

        call.enqueue(new Callback<ListStopPoint>() {
            @Override
            public void onResponse(Call<ListStopPoint> call, Response<ListStopPoint> response) {
                Log.d("GETONEAROUNDCODE", response.code() +"");
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                    Toast.makeText(StartEndLocationSelect.this ,errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                    return;
                }
                ListStopPoint resource = response.body();
                data = resource.getStopPoints();
                String allName = data.size()+"|";
                Log.d("ALLNAMESTOPPOINT", allName);

                generateStopPointMarker1(data);

                //Toast.makeText(getApplicationContext(),data.get(1).getName(),Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(StartEndLocationSelect.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("What Do You Want To Do ?")
                .setItems(selection, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Toast.makeText(getApplicationContext(),"Choose This Stop Point", Toast.LENGTH_SHORT).show();
                                Log.d("StartEndLocation123","choose on marker ID " + marker.getSnippet());

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
}
