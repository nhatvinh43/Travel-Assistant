package com.ygaps.travelapp.data.remote;

import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.ygaps.travelapp.data.model.CommentSend;
import com.ygaps.travelapp.data.model.EditUserInfo;
import com.ygaps.travelapp.data.model.FeedbackSend;
import com.ygaps.travelapp.data.model.Invitation;
import com.ygaps.travelapp.data.model.ListCommentForList;
import com.ygaps.travelapp.data.model.ListFeedbackSP;
import com.ygaps.travelapp.data.model.ListReview;
import com.ygaps.travelapp.data.model.ListStopPoint;
import com.ygaps.travelapp.data.model.ListStopPointSetSP;
import com.ygaps.travelapp.data.model.ListTour;
import com.ygaps.travelapp.data.model.ListTourMyTour;
import com.ygaps.travelapp.data.model.LoginData;
import com.ygaps.travelapp.data.model.MoreOneCoordinate;
import com.ygaps.travelapp.data.model.Notification;
import com.ygaps.travelapp.data.model.NotificationOnRoad;
import com.ygaps.travelapp.data.model.OneCoordinate;
import com.ygaps.travelapp.data.model.PasswordRecoveryOtp;
import com.ygaps.travelapp.data.model.RegisterFCM;
import com.ygaps.travelapp.data.model.RevCoordinate;
import com.ygaps.travelapp.data.model.ReviewSend;
import com.ygaps.travelapp.data.model.SendCoordinate;
import com.ygaps.travelapp.data.model.ServiceDetail;
import com.ygaps.travelapp.data.model.TourCreate;
import com.ygaps.travelapp.data.model.TourInfo;
import com.ygaps.travelapp.data.model.TourResFromTourCreate;
import com.ygaps.travelapp.data.model.UpdateTourInfoRequest;
import com.ygaps.travelapp.data.model.VertifyPasswordRecoveryOtp;

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
    @POST("/user/request-otp-recovery")
    Call<JsonObject> requestPasswordRecoveryOtp(
            @Body PasswordRecoveryOtp passwordRecoveryOtp
    );
    @POST("/user/verify-otp-recovery")
    Call<JsonObject> verifyPasswordRecoveryOtp(
            @Body VertifyPasswordRecoveryOtp vertifyPasswordRecoveryOtp
    );
    @GET("/user/info")
    Call<JsonObject> userInfo(
            @Header("Authorization") String Auth
    );
    @POST("/user/edit-info")
    Call<JsonObject> updateUserInfo(
            @Header("Authorization") String Auth,
            @Body EditUserInfo editUserInfo
    );
    @POST("/tour/set-stop-points")
    Call<JsonObject> setStopPoints(
            @Header("Authorization") String Auth,
            @Body ListStopPointSetSP data
            );

    @POST("/user/notification/put-token")
    Call<JsonObject> registerFCM(
            @Header("Authorization") String Auth,
            @Body RegisterFCM registerFCM
            );
    @POST("/tour/add/member")
    Call<JsonObject> sendInvitation(
            @Header("Authorization") String Auth,
            @Body Invitation invitation
            );
    @POST("/tour/notification")
    Call<JsonObject> sendNotification(
            @Header("Authorization") String Auth,
            @Body Notification notification
            );
    @POST("/tour/add/notification-on-road")
    Call<JsonObject> sendNotificationOnRoad(
            @Header("Authorization") String Auth,
            @Body NotificationOnRoad notificationOnRoad
            );
    @POST("/tour/update-tour")
    Call<JsonObject> updateTour(
            @Header("Authorization") String Auth,
            @Body UpdateTourInfoRequest updateTourInfoRequest
    );
    @GET("/tour/remove-stop-point")
    Call<JsonObject> removeStopPoint(
            @Header("Authorization") String Auth,
            @Query("stopPointId") String stopPointId
    );
    @FormUrlEncoded
    @POST("/tour/update-tour")
    Call<JsonObject> removeTour(
            @Header("Authorization") String Auth,
            @Field("id") String id,
            @Field("status") int status
    );
    @POST("/tour/current-users-coordinate")
    Call<ArrayList<RevCoordinate>> getListCoord(
            @Header("Authorization") String Auth,
            @Body SendCoordinate sendCoordinate
    );
}
