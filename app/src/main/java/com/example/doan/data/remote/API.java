package com.example.doan.data.remote;

import com.example.doan.data.model.ListTour;
import com.example.doan.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    public static String authKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjIsInVzZXJuYW1lIjoidGhpbmg5NyIsImVtYWlsIjoibmd1eWVubWluaHRoaW5oOTdAZ21haWwuY29tIiwiZXhwIjoxNTUzODczNDU1NTY2LCJpYXQiOjE1NTEyODE0NTV9.lQ-RkLSwD3UyRXWvSRaTIsn1f_3ZRMWd-nfRutcwXFw";

    @GET("/tour/list?")
    Call<ListTour> getListTour(@Header("Authorization") String authKey,
                               @Query("pageNum") String page);

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResponse> login(
            @Field("emailPhone") String emailPhone,
            @Field("password") String password
    );
}
