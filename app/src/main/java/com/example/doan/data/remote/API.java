package com.example.doan.data.remote;

import androidx.annotation.Nullable;

import com.example.doan.data.model.CommentForList;
import com.example.doan.data.model.CommentSend;
import com.example.doan.data.model.FeedbackSend;
import com.example.doan.data.model.ListComment;
import com.example.doan.data.model.ListCommentForList;
import com.example.doan.data.model.ListFeedbackSP;
import com.example.doan.data.model.ListReview;
import com.example.doan.data.model.ListStopPoint;
import com.example.doan.data.model.ListTour;
import com.example.doan.data.model.ListTourMyTour;
import com.example.doan.data.model.LoginData;
import com.example.doan.data.model.MoreOneCoordinate;
import com.example.doan.data.model.OneCoordinate;
import com.example.doan.data.model.ReviewSend;
import com.example.doan.data.model.ServiceDetail;
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
    @POST("tour/suggested-destination-list")
    Call<ListStopPoint> moreCoordinate(
            @Header("Authorization") String authKey,
            @Body MoreOneCoordinate moreOneCoordinate
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
    Call<TourResFromTourCreate> createTour(
            @Header("Authorization") String token,
            @Body TourCreate tour
    );

    @GET("/tour/get/service-detail")
    Call<ServiceDetail> getServiceDetail(
            @Header("Authorization") String token,
            @Query("serviceId") Integer Id
    );

    @POST("/tour/comment")
    Call<JsonObject> sendComment(
            @Header("Authorization") String token,
            @Body CommentSend commentSend
    );
    @POST("tour/add/review")
    Call<JsonObject> sendReview(
            @Header("Authorization") String Auth,
            @Body ReviewSend rv
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

    @GET("tour/info")
    Call<TourInfo> getTourInfoTV(
            @Header("Authorization") String Authorization,
            @Query("tourId") int tourId
    );

    @GET("tour/comment-list")
    Call<ListCommentForList> getListComment(
            @Header("Authorization") String Auth,
            @Query("tourId") String id,
            @Query("pageIndex") Integer index,
            @Query("pageSize") String size
    );

    @GET("tour/get/review-list")
    Call<ListReview> getListReview(
            @Header("Authorization") String Auth,
            @Query("tourId") Integer id,
            @Query("pageIndex") Integer index,
            @Query("pageSize") String size
    );

    @GET("tour/get/feedback-service")
    Call<ListFeedbackSP> getListFeedback(
            @Header("Authorization") String Auth,
            @Query("serviceId") Integer id,
            @Query("pageIndex") Integer index,
            @Query("pageSize") String size
    );
    @GET("/tour/history-user")
    Call<ListTourMyTour> getHistoryTour(
            @Header("Authorization") String Authorization,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize
    );
    @POST("/tour/add/feedback-service")
    Call<JsonObject> sendFeedback(
            @Header("Authorization") String Auth,
            @Body FeedbackSend send
    );
}
