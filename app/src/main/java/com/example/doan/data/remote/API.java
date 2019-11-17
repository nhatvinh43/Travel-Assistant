package com.example.doan.data.remote;

import androidx.annotation.Nullable;

import com.example.doan.data.model.ListTour;
import com.google.gson.JsonObject;

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
            @Field("startDate") Integer startDate,
            @Field("endDate") Integer endDate,
            @Field("sourceLat") double sourceLat,
            @Field("sourceLong") double sourceLong,
            @Field("desLat") double desLat,
            @Field("desLong") double desLong,
            @Field("isPrivate") boolean isPrivate,
            @Nullable @Field("adults") Integer adults,
            @Nullable @Field("childs") Integer childs,
            @Nullable @Field("minCost") Integer minCost,
            @Nullable @Field("maxCost") Integer maxCost,
            @Nullable @Field("avatar") String avatar
    );
}
