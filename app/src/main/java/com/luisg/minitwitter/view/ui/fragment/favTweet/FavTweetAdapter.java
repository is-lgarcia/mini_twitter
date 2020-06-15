package com.luisg.minitwitter.view.ui.fragment.favTweet;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luisg.minitwitter.R;
import com.luisg.minitwitter.common.Constants;
import com.luisg.minitwitter.common.SharedPreferencesManager;
import com.luisg.minitwitter.data.TwettViewModel;
import com.luisg.minitwitter.retrofit.response.Like;
import com.luisg.minitwitter.retrofit.response.Tweet;

import java.util.List;

public class FavTweetAdapter extends RecyclerView.Adapter<FavTweetAdapter.ViewHolder> {

    private List<Tweet> mValue;
    private Context context;
    private String username;
    private TwettViewModel twettViewModel;


    public FavTweetAdapter(List<Tweet> mValue, Context context) {
        this.mValue = mValue;
        this.context = context;
        username = SharedPreferencesManager.getSomeStringValue(Constants.PREF_USERNAME);
        twettViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(TwettViewModel.class);
    }

    @NonNull
    @Override
    public FavTweetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.tweet_item, parent, false);

        return new FavTweetAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavTweetAdapter.ViewHolder holder, int position) {

        if (mValue != null) {

            holder.mItem = mValue.get(position);

            holder.textUserName.setText(String.format("@%s", holder.mItem.getUser().getUsername()));
            holder.textMessage.setText(holder.mItem.getMensaje());
            holder.textCountLikes.setText(String.valueOf(holder.mItem.getLikes().size()));

            String photo = holder.mItem.getUser().getPhotoUrl();
            if (!photo.equals("")) {
                Glide
                        .with(context)
                        .load((Constants.API_MINITWITTER_PHOTO_URL + photo))
                        .apply(RequestOptions.circleCropTransform())
                        .into(holder.imageAvatar);
            }

            holder.imageLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    twettViewModel.likeTweet(holder.mItem.getId());
                }
            });

            for (Like like : holder.mItem.getLikes()) {
                if (like.getUsername().equals(username)) {
                    Glide.with(context)
                            .load(R.drawable.ic_like_pink)
                            .into(holder.imageLike);
                    holder.textCountLikes.setTextColor(context.getResources().getColor(R.color.colorPinkIconLike));
                    holder.textCountLikes.setTypeface(null, Typeface.BOLD);
                    break;
                }
            }
        }
    }


    public void setData(List<Tweet> tweetList) {
        this.mValue = tweetList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mValue == null){
            return 0;
        }
        return mValue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView imageAvatar;
        public final ImageView imageLike;
        public final TextView textUserName;
        public final TextView textMessage;
        public final TextView textCountLikes;
        public Tweet mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            imageAvatar = itemView.findViewById(R.id.imageview_avatar);
            imageLike = itemView.findViewById(R.id.image_like);
            textUserName = itemView.findViewById(R.id.text_username);
            textMessage = itemView.findViewById(R.id.text_message);
            textCountLikes = itemView.findViewById(R.id.text_likes);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + textUserName.getText();
        }
    }

}
