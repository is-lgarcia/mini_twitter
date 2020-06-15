package com.luisg.minitwitter.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.luisg.minitwitter.common.Constants;
import com.luisg.minitwitter.common.MyApp;
import com.luisg.minitwitter.common.SharedPreferencesManager;
import com.luisg.minitwitter.retrofit.AuthTwitterClient;
import com.luisg.minitwitter.retrofit.AuthTwitterService;
import com.luisg.minitwitter.retrofit.request.RequestNewTweet;
import com.luisg.minitwitter.retrofit.response.Like;
import com.luisg.minitwitter.retrofit.response.Tweet;
import com.luisg.minitwitter.retrofit.response.TweetDeleted;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {
    AuthTwitterService authTwitterService;
    AuthTwitterClient authTwitterClient;
    MutableLiveData<List<Tweet>> allTweets;
    MutableLiveData<List<Tweet>> favTweets;
    String userName;

    TweetRepository() {
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        allTweets = getAllTweets();
        userName = SharedPreferencesManager.getSomeStringValue(Constants.PREF_USERNAME);
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

    public void likeTweet(final int idTweet) {
        Call<Tweet> call = authTwitterService.likeTweet(idTweet);
        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, final Response<Tweet> response) {
                if (response.isSuccessful()) {

                    final List<Tweet> clonList = new ArrayList<>();
                    for (int i = 0; i < allTweets.getValue().size(); i++) {

                        if (allTweets.getValue().get(i).getId() == idTweet){
                            //Si hemos encontrado en la lista original
                            //el elemento al que se ha dado like,
                            //introducimos el elemento que ha llegado del servidor.
                            clonList.add(response.body());

                        }else {
                            clonList.add(new Tweet(allTweets.getValue().get(i)));
                        }
                    }
                    allTweets.setValue(clonList);
                    getFavTweets();

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

    public MutableLiveData<List<Tweet>> getFavTweets() {
        if (favTweets == null){
            favTweets = new MutableLiveData<>();
        }

        List<Tweet> newFavList = new ArrayList<>();
        Iterator itTweet = allTweets.getValue().iterator();

        while (itTweet.hasNext()){
            Tweet current = (Tweet) itTweet.next();
            Iterator itLikes = current.getLikes().iterator();
            boolean found = false;
            while (itLikes.hasNext() && !found){
                Like like = (Like) itLikes.next();
                if (like.getUsername().equals(userName)){
                    found = true;
                    newFavList.add(current);
                }
            }
        }

        favTweets.setValue(newFavList);

        return favTweets;
    }

    public void deleteTweet(final int idTweet){
        Call<TweetDeleted> call = authTwitterService.deleteTweet(idTweet);
        call.enqueue(new Callback<TweetDeleted>() {
            @Override
            public void onResponse(Call<TweetDeleted> call, Response<TweetDeleted> response) {
                if (response.isSuccessful()){

                    List<Tweet> clonedTweets = new ArrayList<>();
                    for (int i = 0; i < allTweets.getValue().size(); i++) {
                        if (allTweets.getValue().get(i).getId() != idTweet){
                            clonedTweets.add(new Tweet(allTweets.getValue().get(i)));
                        }
                    }
                    allTweets.setValue(clonedTweets);

                }else {
                    Toast.makeText(MyApp.getContext(), "Algo a ido mal, intentelo de nuevo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TweetDeleted> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
