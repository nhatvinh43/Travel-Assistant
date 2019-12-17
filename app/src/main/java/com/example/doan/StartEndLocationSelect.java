package com.example.doan;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import androidx.appcompat.app.AlertDialog;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doan.data.model.Coordinate;
import com.example.doan.data.model.CoordinateSet;
import com.example.doan.data.model.ListStopPoint;
import com.example.doan.data.model.MoreOneCoordinate;
import com.example.doan.data.model.OneCoordinate;
import com.example.doan.data.model.StopPoint;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;
import com.example.doan.ui.login.LoginActivity;
import com.facebook.login.Login;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
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
    private ArrayList<StopPoint> allStopPointToShow1Coor = new ArrayList<>();
    private ArrayList<StopPoint> cacheStopPoint = new ArrayList<>();
    private String[] typeServiceID = {"Restaurant", "Hotel", "Rest Station", "Other"};
    //ic_hotel ic_burger
    private String[] selection = {"Choose", "See Detail"};
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

        //mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //mMap.getUiSettings().setCompassEnabled(true);

        final LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());

        LatLng HCMC = new LatLng(10.775801, 106.693466);

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

        MarkerOptions markerOptions1 = new MarkerOptions().position(latlng1).title("CHO RG").draggable(true).snippet("123");
        MarkerOptions markerOptions2 = new MarkerOptions().position(latlng2).title("AO DINH").draggable(true);
        mMap.addMarker(markerOptions1);
        mMap.addMarker(markerOptions2);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(StartEndLocationSelect.this, marker.getPosition().latitude + " " + marker.getPosition().longitude,Toast.LENGTH_SHORT)
                        .show();
                lat = marker.getPosition().latitude;
                lng = marker.getPosition().longitude;
                return false;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //lay diem dung goi y roi hien thi
                newLatLng = new LatLng(latLng.latitude, latLng.longitude);
                lat = latLng.latitude;
                lng = latLng.longitude;
                mMap.animateCamera(CameraUpdateFactory.newLatLng(newLatLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng,15));
                onTouch(latLng);
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                onHold(latLng);
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showDialog(marker);
                return false;
            }
        });

        //getBound
        //LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;


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

    public void onTouch(final LatLng onTouchLatLng){
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
        Call<ListStopPoint> call = api.oneCoordinate(LoginActivity.TOKEN, oneCoordinate);

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
    public void generateStopPointMarker(ArrayList<StopPoint> data){
        mMap.clear();
        for (StopPoint sp : data){
            LatLng tempPos = new LatLng(Double.valueOf(sp.getLat()), Double.valueOf(sp.get_long()));
            MarkerOptions markerOptions = new MarkerOptions().position(tempPos).snippet(sp.getId().toString())
                    .title(sp.getName() + typeServiceID[sp.getServiceTypeId()]);
            switch (sp.getServiceTypeId() + 1){
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
        cacheStopPoint = data;
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
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(),"See More", Toast.LENGTH_SHORT).show();
                                Intent intent =  new Intent(getApplicationContext(), StopPointInfo_Main.class);
                                if (marker.getSnippet() != ""){
                                    intent.putExtra("StopPointIDForSeeDetail", marker.getSnippet());
                                }
                                startActivity(intent);
                                break;
                        }

                    }
                }).show()
        .getWindow()
        .setGravity(Gravity.BOTTOM);

    }

    public void fetchAllStopPointSuggest(LatLng latLng){

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
