package com.ygaps.travelapp;

import android.content.Context;
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

import androidx.fragment.app.Fragment;

import com.ygaps.travelapp.data.model.ServiceDetail;
import com.ygaps.travelapp.data.remote.API;
import com.ygaps.travelapp.data.remote.retrofit;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StopPointInfo_Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StopPointInfo_Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopPointInfo_Tab1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String StopPointId = "";
    public static String arrivalAt = "";
    public static String leaveAt = "";
    private ServiceDetail serviceDetail;

    private TextView spPrice;
    private TextView spServiceType;
    private TextView spAddress;
    private TextView spStartDate;
    private TextView spEndDate;
    private TextView labelStartDate;
    private TextView labelEndDate;
    private TextView contact;
    private ImageButton editStoppoint;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static int REQUEST_CODE = 22;
    private MyApplication app;
    public StopPointInfo_Tab1() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StopPointInfo_Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static StopPointInfo_Tab1 newInstance(String param1, String param2) {
        StopPointInfo_Tab1 fragment = new StopPointInfo_Tab1();
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
        View view = inflater.inflate(R.layout.fragment_stop_point_info__tab1, container, false);

        spPrice = view.findViewById(R.id.stopPointInfoPrice);
        spServiceType = view.findViewById(R.id.stopPointInfoServiceType);
        spAddress = view.findViewById(R.id.stopPointInfoAddress);
        spStartDate = view.findViewById(R.id.stopPointInfoStartDate);
        spEndDate = view.findViewById(R.id.stopPointInfoEndDate);
        labelStartDate = view.findViewById(R.id.stopPointInfoArrivalDate);
        labelEndDate = view.findViewById(R.id.stopPointInfoLeaveDate);
        contact = view.findViewById(R.id.stopPointInfoContact);
        editStoppoint = view.findViewById(R.id.stopPointInfoEdit);

        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("StopPointId")){
            StopPointId = intent.getStringExtra("StopPointId");
        }
        if (intent.hasExtra("arrivalAt")){
            arrivalAt = intent.getStringExtra("arrivalAt");
        }
        if (intent.hasExtra("leaveAt")){
            leaveAt = intent.getStringExtra("leaveAt");
        }
        app = (MyApplication)getActivity().getApplication();
        if (StopPointInfo_Main.SeeFrom == 1){
            spStartDate.setVisibility(View.GONE);
            spEndDate.setVisibility(View.GONE);
            labelEndDate.setVisibility(View.GONE);
            labelStartDate.setVisibility(View.GONE);
        }

        loadData();

        editStoppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),StopPointInfo_Edit.class);
                intent.putExtra("tourId",TourInfo_Main.tourId);
                intent.putExtra("stoppointId",StopPointInfo_Main.StopPointId);
                intent.putExtra("arrivalAt",arrivalAt);
                intent.putExtra("leaveAt",leaveAt);
                intent.putExtra("lat",serviceDetail.getLat());
                Log.d("StopPointInfo","Tab1 lat" + serviceDetail.getLat());
                intent.putExtra("long",serviceDetail.getLat());
                Log.d("StopPointInfo","Tab1 long" + serviceDetail.getLat());
                intent.putExtra("name",serviceDetail.getName());
                intent.putExtra("minCost",serviceDetail.getMinCost());
                intent.putExtra("maxCost",serviceDetail.getMaxCost());
                intent.putExtra("serivceTypeId",serviceDetail.getServiceTypeId());
                intent.putExtra("contact",serviceDetail.getContact());
                intent.putExtra("address",serviceDetail.getAddress());
                intent.putExtra("provinceId",serviceDetail.getProvinceId());

                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        return view;
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

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        API api = retrofit.getClient().create(API.class);
        Call<ServiceDetail> call = api.getServiceDetail(app.userToken,Integer.valueOf(StopPointInfo_Main.StopPointId));
        call.enqueue(new Callback<ServiceDetail>() {
            @Override
            public void onResponse(Call<ServiceDetail> call, Response<ServiceDetail> response) {
                Log.d("SPInfoMain resCode",response.code()+"");
                if (!response.isSuccessful()){
                    Log.d("SPInforMain Suc", response.isSuccessful()+"");
                    return;
                }
                serviceDetail = response.body();
                String price = serviceDetail.getMinCost()+ "-"+serviceDetail.getMaxCost();
                spPrice.setText(price);
                spServiceType.setText(StartEndLocationSelect.typeServiceID[serviceDetail.getServiceTypeId()]);
                spAddress.setText(serviceDetail.getAddress());
                contact.setText(serviceDetail.getContact());
                StopPointInfo_Main.setSpName(serviceDetail.getName());

                String startDateF = "";
                String endDateF = "";
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if (arrivalAt== ""||leaveAt == ""){
                    startDateF = "Undefined";
                    endDateF = "Undefined";
                }else
                {
                    Long startDateL = Long.valueOf(arrivalAt);
                    startDateF = sdf.format(startDateL);
                    Long endDateL = Long.valueOf(leaveAt);
                    endDateF = sdf.format(endDateL);
                    //neu ngay hom nay lon hon ngay ket thuc thi tat nut bat dau
                }
                spStartDate.setText(startDateF);
                spEndDate.setText(endDateF);
            }

            @Override
            public void onFailure(Call<ServiceDetail> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
