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

import com.example.doan.data.CommentAdapter;
import com.example.doan.data.model.Comment;
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

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Comment> commentList = new ArrayList<>();
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
        final View view = inflater.inflate(R.layout.fragment_tour_info__tab3, container, false);

        recyclerView = view.findViewById(R.id.tourInfoComments);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //get data from server
        API api = getClient().create(API.class);
        Call<JsonObject> call = api.getCommentListTour(LoginActivity.TOKEN,TourInfo_Main.tourId,1,5);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    JsonObject error =gson.fromJson(response.errorBody().charStream(),JsonObject.class);
                    Toast.makeText(getContext(),error.get("message").getAsString(),Toast.LENGTH_LONG).show();
                    return;
                }
                JsonObject commentResponse = response.body();
                if (commentResponse != null) {
                    JsonArray jsonArray = commentResponse.get("commentList").getAsJsonArray();
                    for (int i=0;i<jsonArray.size();i++){
                        JsonObject commentElement = jsonArray.get(i).getAsJsonObject();
                        Comment comment = new Comment();
                        if (!commentElement.get("id").isJsonNull()){
                            comment.setUserId(commentElement.get("id").getAsInt());
                        }
                        if (!commentElement.get("name").isJsonNull()){
                            comment.setName(commentElement.get("name").getAsString());
                        }
                        if (!commentElement.get("comment").isJsonNull()){
                            comment.setComment(commentElement.get("comment").getAsString());
                        }
                        if(!commentElement.get("avatar").isJsonNull()){
                            comment.setAvatar(commentElement.get("avatar").getAsString());
                        }
                        commentList.add(comment);
                    }
                    mAdapter = new CommentAdapter(commentList);
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
