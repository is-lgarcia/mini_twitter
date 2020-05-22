package com.luisg.minitwitter.view.ui.tweet;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luisg.minitwitter.R;
import com.luisg.minitwitter.common.Constants;
import com.luisg.minitwitter.common.SharedPreferencesManager;
import com.luisg.minitwitter.retrofit.response.Like;
import com.luisg.minitwitter.retrofit.response.Tweet;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    List<Tweet> mValue;
    Context context;
    String username;


    public TweetAdapter(List<Tweet> mValue, Context context) {
        this.mValue = mValue;
        this.context = context;
        username = SharedPreferencesManager.getSomeStringValue(Constants.PREF_USERNAME);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_twett, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mItem = mValue.get(position);

        holder.textUserName.setText(holder.mItem.getUser().getUsername());
        holder.textMessage.setText(holder.mItem.getMensaje());
        holder.textCountLikes.setText(holder.mItem.getLikes().size());

        String photo = holder.mItem.getUser().getPhotoUrl();
        if (!photo.equals("")) {
            Glide
                    .with(context)
                    .load((Constants.API_MINITWITTER_BASE_URL + photo))
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imageAvatar);
        }

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

    @Override
    public int getItemCount() {
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
