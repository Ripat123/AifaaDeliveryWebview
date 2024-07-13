package com.Ariyan.demo.network;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.example.com/";

    public static Api getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient())  // Add OkHttpClient for advanced configurations (optional)
                .build();

        return retrofit.create(Api.class);
    }
}
