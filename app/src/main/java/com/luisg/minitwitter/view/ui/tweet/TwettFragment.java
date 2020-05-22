package com.luisg.minitwitter.view.ui.tweet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.luisg.minitwitter.R;
import com.luisg.minitwitter.retrofit.response.Tweet;

import java.util.List;

public class TwettFragment extends Fragment {

    private TwettViewModel twettViewModel;
    RecyclerView recyclerView;
    TweetAdapter adapter;
    List<Tweet> tweetList;



    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        twettViewModel =
                ViewModelProviders.of(this).get(TwettViewModel.class);
        View root = inflater.inflate(R.layout.fragment_twett, container, false);

        //Set Adapter
        if (root instanceof RecyclerView) {
            Context context = root.getContext();
            recyclerView = (RecyclerView) root;
            recyclerView.setHasFixedSize(true);
        }

        loadTwettData();
        
        return root;
    }

    private void loadTwettData() {
        adapter = new TweetAdapter(tweetList,
                getActivity()
        );
        recyclerView.setAdapter(adapter);
    }
}
