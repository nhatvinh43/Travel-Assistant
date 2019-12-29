package com.ygaps.travelapp;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ygaps.travelapp.data.model.TourInfo;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TourInfo_Tab1 extends Fragment {
    public static int REQUEST_CODE = 1;
    private TextView mprice;
    private TextView adults;
    private TextView childs;
    private TextView status;
    private TextView startDate;
    private TextView endDate;
    private TextView pivacy;
    private ImageButton editTourInfo;
    private TextView startTour;
    private ImageButton startTourBtn;
    private ImageButton trackTourBtn;
    private TextView  trackTour;

    public TourInfo_Tab1() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                loadData();
                TourInfo_Main.setTourName(data.getStringExtra("name"));
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tour_info__tab1, container, false);
        mprice = view.findViewById(R.id.tourInfoPrice);
        adults = view.findViewById(R.id.tourInfoAdult);
        childs = view.findViewById(R.id.tourInfoChildren);
        status = view.findViewById(R.id.tourInfoStatus);
        startDate = view.findViewById(R.id.tourInfoStartDate);
        endDate = view.findViewById(R.id.tourInfoEndDate);
        pivacy = view.findViewById(R.id.tourInfoPrivacy);
        editTourInfo = view.findViewById(R.id.tourInfoEditTour);
        startTour = view.findViewById(R.id.startTourText);
        startTourBtn = view.findViewById(R.id.tourInfoStartTour);
        trackTourBtn = view.findViewById(R.id.tourInfoTrackTour);
        trackTour = view.findViewById(R.id.trackTourText);

        loadData();

        trackTourBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),TourInfo_MapScreen.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        if (TourInfo_Main.privacy == 0){
            editTourInfo.setVisibility(View.INVISIBLE);
            startTour.setVisibility(View.GONE);
            startTourBtn.setVisibility(View.GONE);
            trackTour.setVisibility(View.GONE);
            trackTourBtn.setVisibility(View.GONE);
        }else {
            editTourInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), TourInfo_Edit.class);
                    intent.putExtra("tourId", TourInfo_Main.tourId);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        }
        return view;
    }

    private void loadData(){

        API api = retrofit.getClient().create(API.class);
        Call<TourInfo> call = api.getTourInfoTV(((MyApplication)getActivity().getApplication()).userToken,
                Integer.valueOf(TourInfo_Main.tourId));
        call.enqueue(new Callback<TourInfo>() {
            @Override
            public void onResponse(Call<TourInfo> call, Response<TourInfo> response) {
                Log.d("TourInfoTab1 ResCode", response.code()+"");
                if (!response.isSuccessful()){
                    Log.d("TourInfoTab1 Succes",response.isSuccessful()+"");
                    return;
                }

                TourInfo tourInfo = response.body();
                Log.d("TourInfoTab1 Info", tourInfo.getName() + tourInfo.getMinCost() + tourInfo.getMaxCost()
                + tourInfo.getStartDate() + tourInfo.getEndDate() + tourInfo.getChilds() + tourInfo.getAdults());
                String price = tourInfo.getMinCost() + "-" + tourInfo.getMaxCost();
                mprice.setText(price);
                adults.setText(tourInfo.getAdults().toString());
                childs.setText(tourInfo.getChilds().toString());
                status.setText(tourInfo.getStatus().toString());
                if (tourInfo.getIsPrivate()==null){
                    Log.d("IsPrivatex:","nullx");
                    pivacy.setText("Private Tour");
                }
                else {
                    if (tourInfo.getIsPrivate())
                    {
                        pivacy.setText("Private Tour");
                    }
                    else{
                        pivacy.setText("Public Tour");
                    }
                }
                String startDateF = "";
                String endDateF = "";
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if (tourInfo.getStartDate()== null||tourInfo.getEndDate() == null){
                    startDateF = "Undefined";
                    endDateF = "Undefined";
                }else
                {
                    Long startDateL = Long.valueOf(tourInfo.getStartDate());
                    startDateF = sdf.format(startDateL);
                    Long endDateL = Long.valueOf(tourInfo.getEndDate());
                    endDateF = sdf.format(endDateL);
                    //neu ngay hom nay lon hon ngay ket thuc thi tat nut bat dau
                }
                startDate.setText(startDateF);
                endDate.setText(endDateF);
                TourInfo_Main.setTourName(tourInfo.getName());

            }

            @Override
            public void onFailure(Call<TourInfo> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
