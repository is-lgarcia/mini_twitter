package com.luisg.minitwitter.view.ui.fragment.tweet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luisg.minitwitter.R;
import com.luisg.minitwitter.common.Constants;
import com.luisg.minitwitter.common.SharedPreferencesManager;
import com.luisg.minitwitter.data.TwettViewModel;

public class NewTweetDialogFragment extends DialogFragment {

    private ImageView imageClose, imageAvatar;
    private Button btnTwittear;
    private EditText editMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.new_tweet_full_dialog, container, false);
        imageClose = view.findViewById(R.id.image_close_dialog);
        imageAvatar = view.findViewById(R.id.image_avatar_new_tweet);
        btnTwittear = view.findViewById(R.id.button_new_tweet);
        editMessage = view.findViewById(R.id.edit_message);
        btnTwittear.setEnabled(false);


        //Eventos Click
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = editMessage.getText().toString();
                if (mensaje.isEmpty() && mensaje.equals("")) {
                    getDialog().dismiss();
                } else {
                    showDialogConfirm();
                }
            }
        });


        btnTwittear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = editMessage.getText().toString();

                if (mensaje.isEmpty() && mensaje.equals("")) {
                    btnTwittear.setEnabled(false);
                } else {
                    TwettViewModel twettViewModel = new ViewModelProvider(requireActivity()).get(TwettViewModel.class);
                    twettViewModel.insertTweet(mensaje);
                    getDialog().dismiss();
                }
            }
        });
        editMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTwittear.setEnabled(true);
            }
        });
        btnTwittear.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        //Set Profile Image

        String imageUrl = SharedPreferencesManager.getSomeStringValue(Constants.PREF_PHOTOURL);
        if (!imageUrl.equals("")) {
            Glide.with(getActivity())
                    .load(Constants.API_MINITWITTER_PHOTO_URL + imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageAvatar);
        }

        return view;

    }

    public void showDialogConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.alert_message_new_dialog)
                .setTitle(R.string.title_alert_message_dialog)
                .setPositiveButton(R.string.btn_possitive_confir_alert_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getDialog().dismiss();
                    }
                })
                .setNegativeButton(R.string.btn_negative_confir_alert_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();

    }
}
