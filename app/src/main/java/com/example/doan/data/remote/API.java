package com.example.doan.data.remote;

import com.example.doan.data.model.ListTour;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface API {
    @Headers("Autho: ")
    @GET("/")
    Call<ListTour> getListTour();
}
