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
import android.widget.TextView;

import com.example.doan.data.model.TourInfo;

import org.w3c.dom.Text;


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
        TextView price = view.findViewById(R.id.tourInfoPrice);
        TextView adults = view.findViewById(R.id.tourInfoAdult);
        TextView childs = view.findViewById(R.id.tourInfoChildren);
        TextView status = view.findViewById(R.id.tourInfoStatus);



//        price.setText(TourInfo_Main.tourInfo.getMinCost().toString()+ "-" + TourInfo_Main.tourInfo.getMaxCost().toString());
//        adults.setText(TourInfo_Main.tourInfo.getAdults());
//        childs.setText(TourInfo_Main.tourInfo.getChilds());
//        status.setText(TourInfo_Main.tourInfo.getStatus());


        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
