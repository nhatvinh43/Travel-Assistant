package com.ygaps.travelapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.Coordinate;
import com.ygaps.travelapp.data.model.ListStopPoint;
import com.ygaps.travelapp.data.model.OneCoordinate;
import com.ygaps.travelapp.data.model.StopPoint;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragment_explore.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_explore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_explore extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<StopPoint> data = new ArrayList<>();
    private GoogleMap mMap;
    private Location currentLocation;
    private FusedLocationProviderClient client;
    private ImageButton mGetHome;
    private SupportMapFragment supportMapFragment;
    private MyApplication app;
    private MapView mapView;
    private String[] selection = {"See Detail"};
    public fragment_explore() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_explore.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_explore newInstance(String param1, String param2) {
        fragment_explore fragment = new fragment_explore();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_explore, container, false);
//        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.exploreMap);
//        if (supportMapFragment == null){
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            supportMapFragment = SupportMapFragment.newInstance();
//            fragmentTransaction.replace(R.id.exploreMap, supportMapFragment).commit();
//        }
//        supportMapFragment.getMapAsync(this);
        client = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());
        GetLastLocation();
        mGetHome = view.findViewById(R.id.exploreGetCurrentLocation);
        mGetHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext().getApplicationContext(),"Get Current Location",Toast.LENGTH_SHORT).show();
                GetLastLocation();
            }
        });
        app = (MyApplication)getActivity().getApplication();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
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

    }

    private void GetLastLocation(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
            return;
        }
        Task<Location>task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.d("Explore", location.getLatitude() +" "+location.getLongitude());
                if (location!=null){
                    currentLocation = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                            .findFragmentById(R.id.exploreMap);
                    mapFragment.getMapAsync(fragment_explore.this);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 101:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GetLastLocation();
                }
                break;
        }
    }

    private void onTouch(LatLng latLng){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latLng.latitude,latLng.longitude)).zoom(15).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
        mMap.clear();
        data.clear();
        OneCoordinate oneCoordinate = new OneCoordinate(true,
                new Coordinate(latLng.latitude,latLng.longitude));

        API api = retrofit.getClient().create(API.class);
        Call<ListStopPoint> call = api.oneCoordinate(app.userToken, oneCoordinate);
        call.enqueue(new Callback<ListStopPoint>() {
            @Override
            public void onResponse(Call<ListStopPoint> call, Response<ListStopPoint> response) {
                Log.d("GETONEAROUNDCODE", response.code() +"");
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    JsonObject errorLogin =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                    Toast.makeText(getContext(),errorLogin.get("message").getAsString(),Toast.LENGTH_LONG).show();
                    return;
                }
                ListStopPoint resource = response.body();
                data = resource.getStopPoints();
                String allName = data.size()+"|";
                Log.d("ALLNAMESTOPPOINT", allName);

                generateStopPointMarker1(data);
            }

            @Override
            public void onFailure(Call<ListStopPoint> call, Throwable t) {
            }
        });

    }
    private void showDialog(final Marker marker){
        new AlertDialog.Builder(getContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("What Do You Want To Do ?")
                .setItems(selection, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Toast.makeText(getContext(),"See More", Toast.LENGTH_SHORT).show();
                                Intent intent =  new Intent(getContext(), StopPointInfo_Main.class);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
