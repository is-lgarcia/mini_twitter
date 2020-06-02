package com.luisg.minitwitter.view.ui.tweet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luisg.minitwitter.R;
import com.luisg.minitwitter.data.TwettViewModel;
import com.luisg.minitwitter.retrofit.response.Tweet;

import java.util.List;

public class TwettFragment extends Fragment {

    private TwettViewModel twettViewModel;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    TweetAdapter adapter;
    List<Tweet> tweetList;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        twettViewModel = new ViewModelProvider(requireActivity()).get(TwettViewModel.class);
        View root = inflater.inflate(R.layout.fragment_twett, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        progressBar = root.findViewById(R.id.progress_bar_twett);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = root.findViewById(R.id.recycler_view_twett);
        recyclerView.setLayoutManager(layoutManager);


        //Set Adapter
        adapter = new TweetAdapter(tweetList,
                getActivity()
        );
        recyclerView.setAdapter(adapter);
        loadTwettData();
        progressBar.setVisibility(View.GONE);
        
        return root;
    }


    private void loadTwettData() {
        twettViewModel.getTweets().observe(getViewLifecycleOwner(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                tweetList = tweets;
                adapter.setData(tweetList);
            }
        });

    }
}
