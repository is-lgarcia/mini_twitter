package com.luisg.minitwitter.retrofit;

import com.luisg.minitwitter.RequestLogin;
import com.luisg.minitwitter.RequestSignUp;
import com.luisg.minitwitter.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MiniTwitterService {

    @POST("/auth/login")
    Call<ResponseAuth> doLogin(@Body RequestLogin requestLogin);

    @POST("/auth/signup")
    Call<ResponseAuth> doSignUp(@Body RequestSignUp requestSignUp);
}
