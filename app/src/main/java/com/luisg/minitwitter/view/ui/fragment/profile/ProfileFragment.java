package com.luisg.minitwitter.view.ui.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.luisg.minitwitter.R;
import com.luisg.minitwitter.data.TwettViewModel;

public class ProfileFragment extends Fragment {

    private TwettViewModel twettViewModel;
    private ImageView imageAvatar;
    private TextInputLayout inputUserName, inputEmail, inputPassword;
    private Button btnSave, btnChangePassword;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        twettViewModel = new ViewModelProvider(requireActivity()).get(TwettViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        inputUserName = root.findViewById(R.id.input_username_profile);
        inputEmail = root.findViewById(R.id.input_email_profile);
        inputPassword = root.findViewById(R.id.input_password_profile);
        imageAvatar = root.findViewById(R.id.image_view_avatar);
        btnSave = root.findViewById(R.id.btn_save_changes);
        btnChangePassword = root.findViewById(R.id.btn_change_password);
        inputUserName.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
        inputEmail.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
        inputPassword.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));

        //Eventos

        btnSave.setOnClickListener(view -> {
            //TODO guardar datos de perfil
        });

        btnChangePassword.setOnClickListener(view -> {
            //TODO ir a la pantalla de cambiar contraseña
        });

        return root;

    }
}
