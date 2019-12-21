package com.example.doan;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.data.model.TourInfo;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;
import com.example.doan.ui.login.LoginActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TourInfo_Tab1 extends Fragment {


    public TourInfo_Tab1() {
        // Required empty public constructor
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
        final TextView mprice = view.findViewById(R.id.tourInfoPrice);
        final TextView adults = view.findViewById(R.id.tourInfoAdult);
        final TextView childs = view.findViewById(R.id.tourInfoChildren);
        final TextView status = view.findViewById(R.id.tourInfoStatus);
        final TextView startDate = view.findViewById(R.id.tourInfoStartDate);
        final TextView endDate = view.findViewById(R.id.tourInfoEndDate);
        final TextView pivacy = view.findViewById(R.id.tourInfoPrivacy);
        final ImageButton editTourInfo = view.findViewById(R.id.tourInfoEditTour);
        final TextView startTour = view.findViewById(R.id.startTourText);
        final ImageButton startTourBtn = view.findViewById(R.id.tourInfoStartTour);

        if (TourInfo_Main.privacy == 0){
            editTourInfo.setVisibility(View.INVISIBLE);
            startTour.setVisibility(View.GONE);
            startTourBtn.setVisibility(View.GONE);
        }


        API api = retrofit.getClient().create(API.class);
        Call<TourInfo> call = api.getTourInfoTV(LoginActivity.TOKEN, Integer.valueOf(TourInfo_Main.tourId));
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
                    if (tourInfo.getIsPrivate()) {
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


            }

            @Override
            public void onFailure(Call<TourInfo> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
