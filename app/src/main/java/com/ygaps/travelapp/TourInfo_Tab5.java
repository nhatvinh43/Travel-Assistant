package com.ygaps.travelapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.data.model.Member;
import com.ygaps.travelapp.data.model.MemberAdapter;
import com.ygaps.travelapp.data.model.TourInfo;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TourInfo_Tab5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TourInfo_Tab5 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //data

    private ArrayList<Member> dataSet = new ArrayList<>();
    private RecyclerView recyclerView;
    private MemberAdapter adapter;

    public TourInfo_Tab5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TourInfo_Tab5.
     */
    // TODO: Rename and change types and number of parameters
    public static TourInfo_Tab5 newInstance(String param1, String param2) {
        TourInfo_Tab5 fragment = new TourInfo_Tab5();
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
        View view =  inflater.inflate(R.layout.fragment_tour_info__tab5, container, false);
        Button btnAddMem = view.findViewById(R.id.tourInfoAddMember);

        if (TourInfo_Main.privacy == 0){
            btnAddMem.setVisibility(View.INVISIBLE);
        }
        recyclerView = view.findViewById(R.id.tourInfoMembers);
        adapter = new MemberAdapter(getActivity(), dataSet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        fetchDataMember();

        Button addMember = view.findViewById(R.id.tourInfoAddMember);
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),TourInfo_AddMember.class);
                startActivity(intent);
                Toast.makeText(getContext().getApplicationContext(), "Add Member", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void fetchDataMember(){
        API api = retrofit.getClient().create(API.class);
        Call<TourInfo> call = api.getTourInfoTV(((MyApplication)getActivity().getApplication()).userToken, Integer.valueOf(TourInfo_Main.tourId));
        call.enqueue(new Callback<TourInfo>() {
            @Override
            public void onResponse(Call<TourInfo> call, Response<TourInfo> response) {
                Log.d("TourInfoTab5 resCode ", response.code()+"");
                if (!response.isSuccessful()){
                    Log.d("TourInfoTab5 False ", response.isSuccessful()+"");
                    return;
                }
                TourInfo tourInfo = response.body();

                ArrayList<Member> memList = tourInfo.getMembers();
                Log.d("TourInfoTab5 NofMem", tourInfo.getMembers().size()+"/"+memList.size());

                for (int i = 0 ; i <memList.size();i++){
                    Member temp = new Member(memList.get(i).getId(), memList.get(i).getName(), memList.get(i).getPhone(),
                            memList.get(i).getAvatar(),memList.get(i).getIsHost());
                    dataSet.add(temp);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<TourInfo> call, Throwable t) {

            }
        });
    }

}
