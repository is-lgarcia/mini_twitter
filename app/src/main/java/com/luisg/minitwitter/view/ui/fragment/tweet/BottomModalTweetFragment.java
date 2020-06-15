package com.luisg.minitwitter.view.ui.fragment.tweet;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.luisg.minitwitter.R;
import com.luisg.minitwitter.common.Constants;
import com.luisg.minitwitter.data.TwettViewModel;

public class BottomModalTweetFragment extends BottomSheetDialogFragment {

    private TwettViewModel mViewModel;
    private int idTweetEliminar;

    public static BottomModalTweetFragment newInstance(int idTweet) {

        BottomModalTweetFragment fragment = new BottomModalTweetFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_TWEET_ID, idTweet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            idTweetEliminar = getArguments().getInt(Constants.ARG_TWEET_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_modal_tweet_fragment, container, false);

        final NavigationView nav = view.findViewById(R.id.navigation_bottom_tweet);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_detele_tweet){
                    mViewModel.deleteTweet(idTweetEliminar);
                    getDialog().dismiss();
                    return true;
                }

                return false;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(TwettViewModel.class);
    }

}
