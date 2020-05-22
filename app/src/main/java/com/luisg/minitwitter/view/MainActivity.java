package com.luisg.minitwitter.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.luisg.minitwitter.R;
import com.luisg.minitwitter.common.Constants;
import com.luisg.minitwitter.common.SharedPreferencesManager;
import com.luisg.minitwitter.retrofit.MIniTwitterClient;
import com.luisg.minitwitter.retrofit.MiniTwitterService;
import com.luisg.minitwitter.retrofit.request.RequestLogin;
import com.luisg.minitwitter.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView imvLogo;
    TextInputLayout inputEmail, inputPassword;
    Button btnLogin, btnCreateAccount;
    ProgressBar progressBar;
    private MIniTwitterClient mIniTwitterClient;
    private MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofitInit();
        findView();

    }


    private void retrofitInit() {
        mIniTwitterClient = MIniTwitterClient.getInstance();
        miniTwitterService = mIniTwitterClient.getMiniTwitterService();
    }

    private void findView() {
        imvLogo = findViewById(R.id.image_logo);
        inputEmail = findViewById(R.id.input_email_login);
        inputPassword = findViewById(R.id.input_password_login);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progress_bar_login);
        btnCreateAccount = findViewById(R.id.btn_create_account);
    }

    public void onClickLogin(View view) {

        inputEmail.setErrorEnabled(false);
        inputPassword.setErrorEnabled(false);

        String email = inputEmail.getEditText().getText().toString();
        String password = inputPassword.getEditText().getText().toString();

        if (validationLogin(email, password)){

            RequestLogin requestLogin = new RequestLogin(email,password);

            Call<ResponseAuth> call = miniTwitterService.doLogin(requestLogin);
            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if (response.isSuccessful()){
                        progressBar.setVisibility(View.VISIBLE);
                        saveDataSharePreferences(response);
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        inputPassword.setError(getText(R.string.error_login));
                    }
                }
                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {

                }
            });
        }

    }

    private void saveDataSharePreferences(Response<ResponseAuth> response) {
        SharedPreferencesManager.setSomeStringValue(Constants.PREF_TOKEN, response.body().getToken());
        SharedPreferencesManager.setSomeStringValue(Constants.PREF_USERNAME, response.body().getUsername());
        SharedPreferencesManager.setSomeStringValue(Constants.PREF_EMAIL, response.body().getEmail());
        SharedPreferencesManager.setSomeStringValue(Constants.PREF_PHOTOURL, response.body().getPhotoUrl());
        SharedPreferencesManager.setSomeStringValue(Constants.PREF_CREATED, response.body().getCreated());
        SharedPreferencesManager.setSomeBooleanValue(Constants.PREF_ACTIVE, response.body().getActive());
    }

    private boolean validationLogin(String email, String password) {

        if (email.isEmpty()){
            inputEmail.setError(getText(R.string.error_empty));
        } else if (password.isEmpty()){
            inputPassword.setError(getText(R.string.error_empty));
        }else {
            return true;
        }
        return false;
    }

    public void onClickCreateAccount(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);

        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(imvLogo, "image_logo");
        pairs[1] = new Pair<View, String>(inputEmail, "input_email");
        pairs[2] = new Pair<View, String>(inputPassword, "input_password");
        pairs[3] = new Pair<View, String>(btnLogin, "btn_login");
        pairs[4] = new Pair<View, String>(btnCreateAccount, "btn_create");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
        startActivity(intent,options.toBundle());
    }
}
