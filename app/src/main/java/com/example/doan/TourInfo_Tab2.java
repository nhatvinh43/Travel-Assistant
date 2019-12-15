package com.example.doan;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doan.data.CommentAdapter;
import com.example.doan.data.StopPointTourInfoAdapter;
import com.example.doan.data.model.Comment;
import com.example.doan.data.model.StopPointTourInfo;
import com.example.doan.data.remote.API;
import com.example.doan.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.doan.data.remote.retrofit.getClient;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TourInfo_Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TourInfo_Tab2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<StopPointTourInfo> stopPointTourInfoArrayList = new ArrayList<>();

    public TourInfo_Tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TourInfo_Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static TourInfo_Tab2 newInstance(String param1, String param2) {
        TourInfo_Tab2 fragment = new TourInfo_Tab2();
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
        View view = inflater.inflate(R.layout.fragment_tour_info__tab2, container, false);

        recyclerView = view.findViewById(R.id.tourInfoStopPoints);
        Log.d("Stepx","1");
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //get data from server
        API api = getClient().create(API.class);
        Call<JsonObject> call = api.getTourInfo(LoginActivity.TOKEN,Integer.parseInt(TourInfo_Main.tourId));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    JsonObject error =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                    Toast.makeText(getContext(),error.get("message").getAsString(),Toast.LENGTH_LONG).show();
                    return;
                }
                JsonObject tourInfoResponse = response.body();
                if (tourInfoResponse != null) {
                    JsonArray stoppoints = tourInfoResponse.get("stopPoints").getAsJsonArray();
                    for (int i=0;i<stoppoints.size();i++){
                        JsonObject stoppoint = stoppoints.get(i).getAsJsonObject();
                        StopPointTourInfo stopPointTourInfo = new StopPointTourInfo();
                        if (!stoppoint.get("id").isJsonNull()){
                            stopPointTourInfo.setId(stoppoint.get("id").getAsInt());
                        }
                        if (!stoppoint.get("serviceId").isJsonNull()){
                            stopPointTourInfo.setServiceId(stoppoint.get("serviceId").getAsInt());
                        }
                        if (!stoppoint.get("address").isJsonNull()){
                            stopPointTourInfo.setAddress(stoppoint.get("address").getAsString());
                        }
                        if (!stoppoint.get("provinceId").isJsonNull()){
                            stopPointTourInfo.setProvinceId(stoppoint.get("provinceId").getAsInt());
                        }
                        if (!stoppoint.get("name").isJsonNull()){
                            stopPointTourInfo.setName(stoppoint.get("name").getAsString());
                        }
                        if (!stoppoint.get("lat").isJsonNull()){
                            stopPointTourInfo.setLat(stoppoint.get("lat").getAsString());
                        }
                        if (!stoppoint.get("long").isJsonNull()){
                            stopPointTourInfo.setLong(stoppoint.get("long").getAsString());
                        }
                        if (!stoppoint.get("arrivalAt").isJsonNull()){
                            stopPointTourInfo.setArrivalAt( stoppoint.get("arrivalAt").getAsString());
                        }
                        if (!stoppoint.get("leaveAt").isJsonNull()){
                            stopPointTourInfo.setLeaveAt(stoppoint.get("leaveAt").getAsString());
                        }
                        if (!stoppoint.get("minCost").isJsonNull()){
                            stopPointTourInfo.setMinCost(stoppoint.get("minCost").getAsString());
                        }
                        if (!stoppoint.get("maxCost").isJsonNull()){
                            stopPointTourInfo.setMaxCost(stoppoint.get("maxCost").getAsString());
                        }
                        if (!stoppoint.get("serviceTypeId").isJsonNull()){
                            stopPointTourInfo.setServiceTypeId(stoppoint.get("serviceTypeId").getAsInt());
                        }
                        if (!stoppoint.get("avatar").isJsonNull()){
                            stopPointTourInfo.setAvatar(stoppoint.get("avatar").getAsString());
                        }
                        if (!stoppoint.get("index").isJsonNull()){
                            stopPointTourInfo.setIndex(stoppoint.get("index").getAsInt());
                        }
                        stopPointTourInfoArrayList.add(stopPointTourInfo);
                    }
                    mAdapter = new StopPointTourInfoAdapter(stopPointTourInfoArrayList);
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
