package com.Ariyan.demo.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    @GET("/users/{type}")
    Call<String> getLoginID(@Path("type") String type, @Path("delivery_id") String Did,
                            @Path("dob") String date, String token);
}
