package com.luisg.minitwitter.view.ui.favTweet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.luisg.minitwitter.R;
import com.luisg.minitwitter.data.TwettViewModel;
import com.luisg.minitwitter.retrofit.response.Tweet;
import com.luisg.minitwitter.view.ui.tweet.TweetAdapter;

import java.util.List;

public class FavTweetFragment extends Fragment {

    private TwettViewModel twettViewModel;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FavTweetAdapter adapter;
    private List<Tweet> tweetList;
    private SwipeRefreshLayout refreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        twettViewModel = new ViewModelProvider(requireActivity()).get(TwettViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fav_tweet, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        refreshLayout = root.findViewById(R.id.swipe_refres_layout_fav);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                loadNewFav();
            }
        });

        progressBar = root.findViewById(R.id.progress_bar_fav_twett);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = root.findViewById(R.id.recycler_view_fav_twett);
        recyclerView.setLayoutManager(layoutManager);

        //Set Adapter
        adapter = new FavTweetAdapter(tweetList,
                getActivity()
        );
        recyclerView.setAdapter(adapter);
        loadFavTweet();
        progressBar.setVisibility(View.GONE);

        return root;
    }

    private void loadNewFav(){
        twettViewModel.getNewFavTweets().observe(getViewLifecycleOwner(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                tweetList = tweets;
                refreshLayout.setRefreshing(false);
                adapter.setData(tweetList);
                twettViewModel.getNewFavTweets().removeObserver(this);
            }
        });
    }

    private void loadFavTweet(){
        twettViewModel.getFavTweets().observe(getViewLifecycleOwner(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                tweetList = tweets;
                adapter.setData(tweetList);
            }
        });
    }


}
