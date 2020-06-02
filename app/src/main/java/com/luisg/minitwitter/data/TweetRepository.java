package com.luisg.minitwitter.data;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.luisg.minitwitter.common.MyApp;
import com.luisg.minitwitter.retrofit.AuthTwitterClient;
import com.luisg.minitwitter.retrofit.AuthTwitterService;
import com.luisg.minitwitter.retrofit.request.RequestNewTweet;
import com.luisg.minitwitter.retrofit.response.Tweet;
import com.luisg.minitwitter.view.ui.tweet.TweetAdapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {
    AuthTwitterService authTwitterService;
    AuthTwitterClient authTwitterClient;
    MutableLiveData<List<Tweet>> allTweets;

    TweetRepository() {
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        allTweets = getAllTweets();
    }

    public MutableLiveData<List<Tweet>> getAllTweets() {

        if (allTweets == null) {
            allTweets = new MutableLiveData<>();
        }

        Call<List<Tweet>> call = authTwitterService.getAllTeewts();
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if (response.isSuccessful()){

                    allTweets.setValue(response.body());

                } else {
                    Toast.makeText(MyApp.getContext(), "Algo a ido mal...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        return allTweets;
    }

    public void createTweet(String mensaje) {
        RequestNewTweet requestNewTweet = new RequestNewTweet(mensaje);
        Call<Tweet> call = authTwitterService.createTweet(requestNewTweet);
        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, final Response<Tweet> response) {
                if (response.isSuccessful()) {

                    final List<Tweet> clonList = new ArrayList<>();
                    for (int i = 0; i < allTweets.getValue().size(); i++) {
                        clonList.add(new Tweet(allTweets.getValue().get(i)));
                    }
                    allTweets.setValue(clonList);

                } else {
                    Toast.makeText(MyApp.getContext(), "Algo a ido mal, intentelo de nuevo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
