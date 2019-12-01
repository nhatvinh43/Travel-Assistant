package com.example.doan.data.remote;

import androidx.annotation.Nullable;

import com.example.doan.data.model.ListStopPoint;
import com.example.doan.data.model.ListTour;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    //public static String authKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjIsInVzZXJuYW1lIjoidGhpbmg5NyIsImVtYWlsIjoibmd1eWVubWluaHRoaW5oOTdAZ21haWwuY29tIiwiZXhwIjoxNTUzODczNDU1NTY2LCJpYXQiOjE1NTEyODE0NTV9.lQ-RkLSwD3UyRXWvSRaTIsn1f_3ZRMWd-nfRutcwXFw";

    @GET("/tour/list?")
    Call<ListTour> getListTour(@Header("Authorization") String authKey,
                               @Query("pageNum") String page);

    @FormUrlEncoded
    @POST("user/login")
    Call<JsonObject> login(
            @Field("emailPhone") String emailPhone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/user/login/by-facebook")
    Call<JsonObject> loginFacebook(
            @Field("accessToken") String accessToken
    );

    @FormUrlEncoded
    @POST("user/register")
    Call<JsonObject> register(
            @Field("password") String password,
            @Nullable @Field("fullName") String fullName,
            @Field("email") String email,
            @Field("phone") String phone,
            @Nullable @Field("address") String address,
            @Nullable @Field("dob") String dob,
            @Nullable @Field("gender") int gender
    );

    @FormUrlEncoded
    @POST("/tour/create")
    Call<JsonObject> createTour(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("startDate") int startDate,
            @Field("endDate") int endDate,
            @Field("sourceLat") double sourceLat,
            @Field("sourceLong") double sourceLong,
            @Field("desLat") double desLat,
            @Field("desLong") double desLong,
            @Field("isPrivate") boolean isPrivate,
            @Nullable @Field("adults") int adults,
            @Nullable @Field("childs") int childs,
            @Nullable @Field("minCost") int minCost,
            @Nullable @Field("maxCost") int maxCost,
            @Nullable @Field("avatar") String avatar
    );
    @FormUrlEncoded
    @POST("/tour/suggested-destination-list")
    Call<ListStopPoint> getSuggestDes(
            @Header("Authorization") String token,
            @Field("hasOneCoordinate") Boolean orNot,
            @Field("coordList") ArrayList<LatLng> listCoord
            );
}
