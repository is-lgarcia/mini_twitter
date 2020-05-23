package com.luisg.minitwitter.view.ui.tweet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luisg.minitwitter.R;
import com.luisg.minitwitter.retrofit.AuthTwitterClient;
import com.luisg.minitwitter.retrofit.AuthTwitterService;
import com.luisg.minitwitter.retrofit.response.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TwettFragment extends Fragment {

    private TwettViewModel twettViewModel;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    TweetAdapter adapter;
    List<Tweet> tweetList;
    AuthTwitterService authTwitterService;
    AuthTwitterClient authTwitterClient;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        twettViewModel =
                ViewModelProviders.of(this).get(TwettViewModel.class);
        View root = inflater.inflate(R.layout.fragment_twett, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        progressBar = root.findViewById(R.id.progress_bar_twett);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = root.findViewById(R.id.recycler_view_twett);
        recyclerView.setLayoutManager(layoutManager);
        //Set Adapter
        retrofitInit();
        loadTwettData();
        
        return root;
    }

    private void retrofitInit() {
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();

    }

    private void loadTwettData() {

        Call<List<Tweet>> call = authTwitterService.getAllTeewts();
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if (response.isSuccessful()){
                    tweetList = response.body();

                    adapter = new TweetAdapter(tweetList,
                            getActivity()
                    );
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getActivity(), "Algo a ido mal...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
