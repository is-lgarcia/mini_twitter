package com.luisg.minitwitter.view.ui.fragment.profile;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.luisg.minitwitter.R;
import com.luisg.minitwitter.common.Constants;
import com.luisg.minitwitter.data.ProfileViewModel;
import com.luisg.minitwitter.retrofit.request.RequestUserProfile;
import com.luisg.minitwitter.retrofit.response.ResponseUserProfile;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private ImageView imageAvatar;
    private FloatingActionButton fabUploadPhoto;
    private TextInputLayout inputUserName, inputEmail, inputPassword, inputWebsite, inputDescription;
    private Button btnSave, btnChangePassword;
    boolean loadingData = true;
    PermissionListener allPermisionListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        findView(root);

        //Eventos

        btnSave.setOnClickListener(view -> {

            String username = inputUserName.getEditText().getText().toString();
            String email = inputEmail.getEditText().getText().toString();
            String website = inputWebsite.getEditText().getText().toString();
            String description = inputDescription.getEditText().getText().toString();
            String password = inputPassword.getEditText().getText().toString();


            if (username.isEmpty()){
                inputUserName.setError(getText(R.string.error_empty));
            } else if (email.isEmpty()) {
                inputEmail.setError(getText(R.string.error_empty));
            } else if (password.isEmpty()) {
                inputPassword.setError(getText(R.string.error_empty));
            } else {

                RequestUserProfile requestUserProfile = new RequestUserProfile(
                        username, email, description, website, password
                );
                profileViewModel.updateProfile(requestUserProfile);
                btnSave.setEnabled(false);
            }
        });

        btnChangePassword.setOnClickListener(view -> {
            //TODO ir a la pantalla de cambiar contraseña
        });

        fabUploadPhoto.setOnClickListener( view -> {
            //Invocamos al metodo de comprobacion de permisos
            checkPermission();
        });

        profileViewModel.userProfileLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseUserProfile>() {
            @Override
            public void onChanged(ResponseUserProfile responseUserProfile) {
                loadingData = false;
                inputUserName.getEditText().setText(responseUserProfile.getUsername());
                inputEmail.getEditText().setText(responseUserProfile.getEmail());
                inputWebsite.getEditText().setText(responseUserProfile.getWebsite());
                inputDescription.getEditText().setText(responseUserProfile.getDescripcion());


                if (!responseUserProfile.getPhotoUrl().isEmpty()){
                    Glide.with(getActivity())
                            .load(Constants.API_MINITWITTER_PHOTO_URL + responseUserProfile.getPhotoUrl())
                            .apply(RequestOptions.circleCropTransform())
                            .skipMemoryCache(true)
                            .centerCrop()
                            .into(imageAvatar);
                }

                if (!loadingData) {
                    btnSave.setEnabled(true);
                    Toast.makeText(getActivity(), "Datos Guardados Correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

        profileViewModel.photoProfile.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(getActivity())
                        .load(Constants.API_MINITWITTER_PHOTO_URL + s)
                        .apply(RequestOptions.circleCropTransform())
                        .skipMemoryCache(true)
                        .centerCrop()
                        .into(imageAvatar);
            }
        });

        return root;



    }

    private void checkPermission() {
        PermissionListener dialogOnDeniedPermissionListener =
                DialogOnDeniedPermissionListener.Builder.withContext(getActivity())
                .withTitle(R.string.title_permissions)
                .withMessage(R.string.message_permission)
                .withButtonText("Aceptar")
                .withIcon(R.mipmap.ic_launcher)
                .build();

        allPermisionListener = new CompositePermissionListener(
                (PermissionListener) getActivity(),dialogOnDeniedPermissionListener
        );

        Dexter.withContext(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(allPermisionListener)
                .check();
    }

    private void findView(View root) {
        inputUserName = root.findViewById(R.id.input_username_profile);
        inputEmail = root.findViewById(R.id.input_email_profile);
        inputPassword = root.findViewById(R.id.input_password_profile);
        inputWebsite = root.findViewById(R.id.input_website_profile);
        inputDescription = root.findViewById(R.id.input_description_profile);
        imageAvatar = root.findViewById(R.id.image_view_avatar);
        btnSave = root.findViewById(R.id.btn_save_changes);
        fabUploadPhoto = root.findViewById(R.id.fab_add_photo);
        btnChangePassword = root.findViewById(R.id.btn_change_password);
        inputUserName.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
        inputEmail.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
        inputPassword.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
        inputWebsite.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
        inputDescription.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
    }
}
