package com.luisg.minitwitter.retrofit;

import com.luisg.minitwitter.retrofit.request.RequestNewTweet;
import com.luisg.minitwitter.retrofit.response.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface AuthTwitterService {

    @GET("tweets/all")
    Call<List<Tweet>> getAllTeewts();

    @POST("tweets/create")
    Call<Tweet> createTweet(@Body RequestNewTweet requestNewTweet);

}
