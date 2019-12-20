package com.example.doan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.doan.data.model.FeedbackSP;
import com.example.doan.data.model.FeedbackSPAdapter;
import com.example.doan.data.model.ListFeedbackSP;
import com.example.doan.data.remote.API;
import com.example.doan.data.remote.retrofit;
import com.example.doan.ui.login.LoginActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StopPointInfo_Tab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StopPointInfo_Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopPointInfo_Tab2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<FeedbackSP> dataSet = new ArrayList<>();
    private RecyclerView recyclerView;
    private FeedbackSPAdapter adapter;
    private Button rate;

    public StopPointInfo_Tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StopPointInfo_Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static StopPointInfo_Tab2 newInstance(String param1, String param2) {
        StopPointInfo_Tab2 fragment = new StopPointInfo_Tab2();
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
        View view = inflater.inflate(R.layout.fragment_stop_point_info__tab2, container, false);

        Button rate = (Button) view.findViewById(R.id.stopPointInfoRatings);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StopPointInfo_Rate.class);
                startActivity(intent);
            }
        });

        rate = view.findViewById(R.id.stopPointInfoRatings);
        recyclerView = view.findViewById(R.id.stopPointInfoComments);
        adapter = new FeedbackSPAdapter(getActivity(),dataSet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        fetchFeedbackData();

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StopPointInfo_Rate.class);
                //startActivityForResult(intent, 1001); //
                startActivity(intent);
            }
        });
        return view;
    }

    private void fetchFeedbackData(){
        dataSet.clear();
        final API api = retrofit.getClient().create(API.class);
        Call<ListFeedbackSP>call = api.getListFeedback(LoginActivity.TOKEN, Integer.valueOf(StopPointInfo_Main.StopPointId),
                1,"10");
        call.enqueue(new Callback<ListFeedbackSP>() {
            @Override
            public void onResponse(Call<ListFeedbackSP> call, Response<ListFeedbackSP> response) {
                Log.d("SPInfoTab2 ResCode", response.code()+"");
                if (!response.isSuccessful()){
                    Log.d("SPInfoTab2 Fail", response.isSuccessful()+"");
                    return;
                }
                ListFeedbackSP listFeedbackSP = response.body();
                Log.d("SPInfoTab2", "Size of ListFB" + listFeedbackSP.getFeedbackList().size());
                ArrayList<FeedbackSP> data = listFeedbackSP.getFeedbackList();
                for (int i =0 ; i< data.size();i++){
                    FeedbackSP temp = new FeedbackSP(data.get(i).getId(),data.get(i).getUserId(),data.get(i).getName(),
                            data.get(i).getPhone(), data.get(i).getAvatar(), data.get(i).getFeedback(), data.get(i).getPoint(),
                            data.get(i).getCreatedOn());
                    dataSet.add(temp);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ListFeedbackSP> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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
