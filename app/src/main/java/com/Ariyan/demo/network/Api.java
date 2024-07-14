package com.Ariyan.demo.network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    @POST("/users/{type}")
    Call<String> getLoginID(@Path("type") String type, @Path("delivery_id") String Did,
                            @Path("dob") String date, String token);
    @POST("")
    Call<String> UpdateToken(@Body Map<String, String> map);
}
