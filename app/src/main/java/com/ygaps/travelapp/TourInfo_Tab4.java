package com.ygaps.travelapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TourInfo_Tab4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TourInfo_Tab4 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TourInfo_Tab4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TourInfo_Tab4.
     */
    // TODO: Rename and change types and number of parameters
    public static TourInfo_Tab4 newInstance(String param1, String param2) {
        TourInfo_Tab4 fragment = new TourInfo_Tab4();
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
        View view = inflater.inflate(R.layout.fragment_tour_info__tab4, container, false);
        EditText chatContent = view.findViewById(R.id.tourInfoChatContent);
        ImageButton chatBtn = view.findViewById(R.id.tourInfoChatSendBtn);
        if (TourInfo_Main.privacy == 0){
            chatContent.setVisibility(View.GONE);
            chatBtn.setVisibility(View.GONE);
            Toast.makeText(getContext().getApplicationContext(), "You Cannot Use That Feature Now", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext().getApplicationContext(), "See Your Chat", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

}
