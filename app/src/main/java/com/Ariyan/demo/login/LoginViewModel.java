package com.Ariyan.demo.login;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Ariyan.demo.network.Api;
import com.Ariyan.demo.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private Api api;
    private MutableLiveData<String> data;

    protected LiveData<String> Login(String type,String Did, String date,String token){
        data = new MutableLiveData<>();
        loginFun(type, Did, date,token);
        return data;
    }

    private void loginFun(String type, String did, String date,String token) {
        try {data.setValue("s");
            api = RetrofitClient.getInstance();
            api.getLoginID(type,did,date,token).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                     data.setValue("s");
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
