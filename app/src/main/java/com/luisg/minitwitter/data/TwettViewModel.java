package com.luisg.minitwitter.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luisg.minitwitter.retrofit.response.Tweet;

import java.util.List;

public class TwettViewModel extends AndroidViewModel {

    private TweetRepository repository;
    private LiveData<List<Tweet>> allTweets;


    public TwettViewModel(@NonNull Application application) {
        super(application);
        repository = new TweetRepository();
        allTweets = repository.getAllTweets();
    }

    public LiveData<List<Tweet>> getTweets(){ return allTweets;}

    public void insertTweet(String message) {
        repository.createTweet(message);
    }
}