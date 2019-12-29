package com.ygaps.travelapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.data.model.StopPointTourInfo;
import com.ygaps.travelapp.data.model.StopPointTourInfoAdapter;
import com.ygaps.travelapp.data.model.TourInfo;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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


    RecyclerView recyclerView;
    StopPointTourInfoAdapter adapter;
    ArrayList<StopPointTourInfo> dataSet = new ArrayList<>();
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
        ImageButton editStopPoint = view.findViewById(R.id.tourInfoEditStopPoints);

        if (TourInfo_Main.privacy==0){
            editStopPoint.setVisibility(View.INVISIBLE);
        }

        editStopPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), StopPoints.class);
                intent.putExtra("TourId", TourInfo_Main.tourId);
                //startActivity(intent);
                startActivityForResult(intent,111);
            }
        });
        recyclerView = view.findViewById(R.id.tourInfoStopPoints);
        adapter = new StopPointTourInfoAdapter(getActivity(), dataSet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        //fetchStopPointData();

        //get data from server


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==111){
            if (resultCode==getActivity().RESULT_OK){
                fetchStopPointData();
                getActivity().recreate();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchStopPointData();
    }

    public void fetchStopPointData(){
        dataSet.clear();
        final API api = retrofit.getClient().create(API.class);
        Call<TourInfo> call = api.getTourInfoTV(((MyApplication)getActivity().getApplication()).userToken,Integer.valueOf(TourInfo_Main.tourId));
        call.enqueue(new Callback<TourInfo>() {
            @Override
            public void onResponse(Call<TourInfo> call, Response<TourInfo> response) {
                Log.d("TourInfoTab2", "RESCODE" + response.code());
                if (!response.isSuccessful()){
                    return;
                }
                TourInfo resource = response.body();
                ArrayList<StopPointTourInfo> data = resource.getStopPoints();
                for (int i =0 ; i<data.size();i++){
                    StopPointTourInfo temp = new StopPointTourInfo(data.get(i).getId(),data.get(i).getServiceId(), data.get(i).getAddress(),
                            data.get(i).getProvinceId(), data.get(i).getName(),data.get(i).getLat(), data.get(i).getLong(), data.get(i).getArrivalAt(),
                            data.get(i).getLeaveAt(), data.get(i).getMinCost(),data.get(i).getMaxCost(),data.get(i).getServiceTypeId(),
                            data.get(i).getAvatar(), data.get(i).getIndex());
                    dataSet.add(temp);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TourInfo> call, Throwable t) {

            }
        });
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
