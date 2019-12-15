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
import android.widget.Toast;

import com.example.doan.data.model.Coordinate;
import com.example.doan.data.model.ListStopPoint;
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

    private ArrayList<StopPoint> allStopPointToShow1Coor = new ArrayList<>();
    private String[] typeServiceID = {"Restaurant", "Hotel", "Rest Station", "Other"};
    //ic_hotel ic_burger
    private String[] selection = {"Add This Stop Point To Tour", "See Detail"};
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
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I AM HERE").snippet("TEST")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.boy));

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        mMap.addMarker(markerOptions);

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
        //showAllStopPoint();

    }

    //
    private void showAllStopPoint(ArrayList<StopPoint> data){
        //Clear and re-show all.
        //refresh
        //Toast.makeText(getApplicationContext(),"Show All Stop Points: New and from API suggest", Toast.LENGTH_SHORT).show();
        for (StopPoint sp : data){
            String title = typeServiceID[sp.getServiceTypeId()];
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_burger);
            LatLng position = new LatLng(Double.valueOf(sp.getLat()), Double.valueOf(sp.get_long()));
//            switch (sp.getServiceTypeId()){
//                case 1:
//                    //Restaurant
//                    icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_burger);
//                    break;
//                case 2:
//                    //hotel
//                    icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_hotel);
//                    break;
//                case 3:
//                    //Rest Station
//                    icon = BitmapDescriptorFactory.fromResource(R.drawable.boy);
//                    break;
//                case 4:
//                    //other
//                    icon = BitmapDescriptorFactory.fromResource(R.drawable.com_facebook_button_icon);
//                    break;
//            }
            MarkerOptions markerOptions = new MarkerOptions().position(position).snippet(sp.getId().toString())
                    .icon(icon).title(title);
            mMap.addMarker(markerOptions);
        }
    }


    public void onTouch(final LatLng onTouchLatLng){
        mMap.clear();

        //show AlertDialog to Notify See Detail or Choose.

        //get Bounds
        LatLngBounds latLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        LatLng nearLeft, nearRight, farLeft, farRight;
        nearLeft = mMap.getProjection().getVisibleRegion().nearLeft;


        OneCoordinate oneCoordinate = new OneCoordinate(true,
                new Coordinate(onTouchLatLng.latitude,onTouchLatLng.longitude));

        API api = retrofit.getClient().create(API.class);
        Call<ListStopPoint> call = api.oneCoordinate(LoginActivity.TOKEN, oneCoordinate);
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
                ArrayList<StopPoint> data = resource.getStopPoints();
                String allName = data.size()+"|";
                for (StopPoint d : data){
                    allName = allName + d.getName()+"_"+d.getServiceTypeId()+" ";
                    LatLng tempPos = new LatLng(Double.valueOf(d.getLat()), Double.valueOf(d.get_long()));
                    //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.boy);
                    MarkerOptions markerOptions = new MarkerOptions().position(tempPos).title(d.getName()+typeServiceID[d.getServiceTypeId()])
                            .snippet(d.getId().toString());
                    switch (d.getServiceTypeId()+1){
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
                Log.d("ALLNAMESTOPPOINT", allName);
                //showAllStopPoint(data);




                //Toast.makeText(getApplicationContext(),data.get(1).getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ListStopPoint> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

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
                                    intent.putExtra("StopPointID", marker.getSnippet());
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
