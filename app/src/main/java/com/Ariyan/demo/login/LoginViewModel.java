package com.Ariyan.demo.login;

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

    protected LiveData<String> Login(String type,String Did, String date){
        data = new MutableLiveData<>();
        loginFun(type, Did, date);
        return data;
    }

    private void loginFun(String type, String did, String date) {
        try {data.setValue("s");
            api = RetrofitClient.getInstance();
            api.getLoginID(type,did,date).enqueue(new Callback<String>() {
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
