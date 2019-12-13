package com.example.doan.data.remote;

import androidx.annotation.Nullable;

import com.example.doan.data.model.CommentSend;
import com.example.doan.data.model.ListStopPoint;
import com.example.doan.data.model.ListTour;
import com.example.doan.data.model.ListTourMyTour;
import com.example.doan.data.model.LoginData;
import com.example.doan.data.model.OneCoordinate;
import com.example.doan.data.model.TourCreate;
import com.example.doan.data.model.TourInfo;
import com.example.doan.data.model.TourResFromTourCreate;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET("/tour/history-user")
    Call<ListTourMyTour> getListTourMyTour(
            @Header("Authorization") String authKey,
            @Query("pageIndex") Integer index,
            @Query("pageSize") String size
            );
    @FormUrlEncoded
    @POST("user/login")
    Call<JsonObject> login(
            @Field("emailPhone") String emailPhone,
            @Field("password") String password
    );

    @POST("/user/login")
    Call<JsonObject> getLogin(
            @Body LoginData loginData
            );
    @FormUrlEncoded
    @POST("/user/login/by-facebook")
    Call<JsonObject> loginFacebook(
            @Field("accessToken") String accessToken
    );

    ///tour/suggested-destination-list
    @POST("/tour/suggested-destination-list")
    Call<ListStopPoint> oneCoordinate(
            @Header("Authorization") String authKey,
            @Body OneCoordinate oneCoordinate
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

    @POST("/tour/create")
    Call<JsonObject> createTour(
            @Header("Authorization") String token,
            @Body TourCreate tour
    );

    @POST("/tour/comment")
    Call<JsonObject> sendComment(
            @Header("Authorization") String token,
            @Body CommentSend commentSend
    );

    @GET("/tour/comment-list")
    Call<JsonObject> getCommentListTour(
            @Header("Authorization") String Authorization,
            @Query("tourId") String tourId,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize
    );

    @GET("/tour/info")
    Call<JsonObject> getTourInfo(
            @Header("Authorization") String Authorization,
            @Query("tourId") int tourId
    );
}
