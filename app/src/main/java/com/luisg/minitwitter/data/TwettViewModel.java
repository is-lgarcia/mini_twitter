package com.luisg.minitwitter.data;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.luisg.minitwitter.retrofit.response.Tweet;
import com.luisg.minitwitter.view.ui.fragment.tweet.BottomModalTweetFragment;

import java.util.List;

public class TwettViewModel extends AndroidViewModel {

    private TweetRepository repository;
    private LiveData<List<Tweet>> allTweets;
    private LiveData<List<Tweet>> favTweets;


    public TwettViewModel(@NonNull Application application) {
        super(application);
        repository = new TweetRepository();
        allTweets = repository.getAllTweets();
    }

    public LiveData<List<Tweet>> getTweets(){ return allTweets;}

    public LiveData<List<Tweet>> getFavTweets(){
        allTweets = repository.getFavTweets();
        return allTweets;
    }

    public LiveData<List<Tweet>> getNewFavTweets(){
        getNewTweets();
        return getFavTweets();
    }

    public LiveData<List<Tweet>> getNewTweets(){
        allTweets = repository.getAllTweets();
        return allTweets;
    }

    public void insertTweet(String message) {
        repository.createTweet(message);
    }

    public void likeTweet(int idTweet){
        repository.likeTweet(idTweet);
    }

    public void deleteTweet(int idTweet) { repository.deleteTweet(idTweet);}

    public void showDialogTweetMenu(Context context, int idTweet){
        BottomModalTweetFragment dialogTweet = BottomModalTweetFragment.newInstance(idTweet);
        dialogTweet.show(((AppCompatActivity)context).getSupportFragmentManager(),"BottomModalTweetFragment");
    }


}