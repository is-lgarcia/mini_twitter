package com.luisg.minitwitter.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.luisg.minitwitter.common.Constants;
import com.luisg.minitwitter.common.MyApp;
import com.luisg.minitwitter.common.SharedPreferencesManager;
import com.luisg.minitwitter.retrofit.AuthTwitterClient;
import com.luisg.minitwitter.retrofit.AuthTwitterService;
import com.luisg.minitwitter.retrofit.request.RequestNewTweet;
import com.luisg.minitwitter.retrofit.request.RequestUserProfile;
import com.luisg.minitwitter.retrofit.response.Like;
import com.luisg.minitwitter.retrofit.response.ResponseUserProfile;
import com.luisg.minitwitter.retrofit.response.Tweet;
import com.luisg.minitwitter.retrofit.response.TweetDeleted;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    AuthTwitterService authTwitterService;
    AuthTwitterClient authTwitterClient;
    MutableLiveData<ResponseUserProfile> userProfile;

    ProfileRepository() {
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        userProfile = getProfile();
    }

    //Users

    public MutableLiveData<ResponseUserProfile> getProfile() {

        if (userProfile == null) {
            userProfile = new MutableLiveData<>();
        }

        Call<ResponseUserProfile> call = authTwitterService.getProfile();
        call.enqueue(new Callback<ResponseUserProfile>() {
            @Override
            public void onResponse(Call<ResponseUserProfile> call, Response<ResponseUserProfile> response) {
                if (response.isSuccessful()) {
                    userProfile.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), "Algo a ido mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUserProfile> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });

        return userProfile;
    }

    public void updateProfile(RequestUserProfile requestUserProfile) {
        Call<ResponseUserProfile> call = authTwitterService.updateProfile(requestUserProfile);
        call.enqueue(new Callback<ResponseUserProfile>() {
            @Override
            public void onResponse(Call<ResponseUserProfile> call, Response<ResponseUserProfile> response) {
                if (response.isSuccessful()) {
                    userProfile.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), "Algo a ido mal", Toast.LENGTH_SHORT).show();
                }

                }

                @Override
                public void onFailure (Call < ResponseUserProfile > call, Throwable t){
                    Toast.makeText(MyApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
