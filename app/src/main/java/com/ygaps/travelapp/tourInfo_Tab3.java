package com.ygaps.travelapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.ygaps.travelapp.data.model.CommentAdapter;
import com.ygaps.travelapp.data.model.CommentForList;
import com.ygaps.travelapp.data.model.ListCommentForList;
import com.ygaps.travelapp.data.model.ListReview;
import com.ygaps.travelapp.data.model.Review;
import com.ygaps.travelapp.data.remote.API;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ygaps.travelapp.data.remote.retrofit.getClient;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tourInfo_Tab3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tourInfo_Tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tourInfo_Tab3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<Review> reviews = new ArrayList<>();
    private ArrayList<CommentForList> cmts = new ArrayList<>();
    private ArrayList<Review> dataSet = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommentAdapter adapter;



    public tourInfo_Tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tourInfo_Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static tourInfo_Tab3 newInstance(String param1, String param2) {
        tourInfo_Tab3 fragment = new tourInfo_Tab3();
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
        View view = inflater.inflate(R.layout.fragment_tour_info__tab3, container, false);

        recyclerView = view.findViewById(R.id.tourInfoComments);
        adapter = new CommentAdapter(getActivity(), dataSet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fetchAllCommentNReview();

        adapter.notifyDataSetChanged();

        Button rate = (Button) view.findViewById(R.id.tourInfoRatings);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourInfo_Rate.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void fetchAllCommentNReview(){
        API api = getClient().create(API.class);
        Call<ListCommentForList> call1 = api.getListComment(((MyApplication) getActivity().getApplication()).userToken,
                TourInfo_Main.tourId, 1, "10");
        Call<ListReview> call2 = api.getListReview(((MyApplication)getActivity().getApplication()).userToken,
                Integer.valueOf(TourInfo_Main.tourId),1, "10");
        call1.enqueue(new Callback<ListCommentForList>() {
            @Override
            public void onResponse(Call<ListCommentForList> call1, Response<ListCommentForList> response) {
                Log.d("TourInfoTab3", "RESCODE CMT" + response.code());
                if (!response.isSuccessful()){
                    return;
                }
                ListCommentForList resource = response.body();
                Log.d("TourInfoTab3","NumofCmt"+resource.getCommentForLists().size());
                cmts = resource.getCommentForLists();
                for (int i =0 ; i < cmts.size();i++){
                    Review temp = new Review(cmts.get(i).getId(),cmts.get(i).getName(),
                            cmts.get(i).getAvatar(),cmts.get(i).getComment(),0,cmts.get(i).getCreatedOn());
                    Log.d("TourInfoTab3", "RV Point "+temp.getPoint());
                    dataSet.add(temp);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ListCommentForList> call1, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        call2.enqueue(new Callback<ListReview>() {
            @Override
            public void onResponse(Call<ListReview> call2, Response<ListReview> response) {
                Log.d("TourInfoTab3", "RESCODE RV" + response.code());
                if (!response.isSuccessful()){
                    return;
                }
                ListReview resource = response.body();

                reviews = resource.getReviewList();
                Log.d("TourInfoTab3","NumOfRV"+reviews.size());
                for (int i =0 ; i < reviews.size();i++){
                    Review temp = new Review(reviews.get(i).getId(),reviews.get(i).getName(),reviews.get(i).getAvatar(),
                            reviews.get(i).getReview(), reviews.get(i).getPoint(),reviews.get(i).getCreatedOn());
                    Log.d("TourInfoTab3", "RV Point "+temp.getPoint());
                    dataSet.add(temp);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ListReview> call2, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
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
