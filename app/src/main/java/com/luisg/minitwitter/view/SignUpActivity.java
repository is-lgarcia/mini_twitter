package com.luisg.minitwitter.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.luisg.minitwitter.R;
import com.luisg.minitwitter.common.Constants;
import com.luisg.minitwitter.common.SharedPreferencesManager;
import com.luisg.minitwitter.retrofit.MIniTwitterClient;
import com.luisg.minitwitter.retrofit.MiniTwitterService;
import com.luisg.minitwitter.retrofit.request.RequestSignUp;
import com.luisg.minitwitter.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp, btnBackLogin;
    TextInputLayout inputUsername, inputEmail, inputPassword;
    ProgressBar progressBar;
    MiniTwitterService miniTwitterService;
    MIniTwitterClient mIniTwitterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        retrofitInit();
        findView();
        backLogin();
    }

    private void retrofitInit() {
        mIniTwitterClient = MIniTwitterClient.getInstance();
        miniTwitterService = mIniTwitterClient.getMiniTwitterService();

    }

    private void backLogin() {
        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void findView() {
        btnSignUp = findViewById(R.id.btn_signup);
        btnBackLogin = findViewById(R.id.btn_back_login);
        inputUsername = findViewById(R.id.input_user_name_signup);
        inputEmail = findViewById(R.id.input_email_signup);
        inputPassword = findViewById(R.id.input_password_signup);
        progressBar = findViewById(R.id.progressBarSignUp);
    }

    public void onClickSignUp(View view) {
        progressBar.setVisibility(View.VISIBLE);
        inputUsername.setErrorEnabled(false);
        inputEmail.setErrorEnabled(false);
        inputPassword.setErrorEnabled(false);


        final String username = inputUsername.getEditText().getText().toString();
        String email = inputEmail.getEditText().getText().toString();
        String password = inputPassword.getEditText().getText().toString();

        if (validationSignUP(username, email, password)){
            RequestSignUp requestSignUp = new RequestSignUp(username, email, password, Constants.CODE_ACCES_API);
            Call<ResponseAuth> call = miniTwitterService.doSignUp(requestSignUp);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if (response.isSuccessful()){
                        progressBar.setVisibility(View.VISIBLE);
                        saveDataSharePreferences(response);
                        Intent intent = new Intent(SignUpActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    progressBar.setVisibility(View.GONE);
                    inputUsername.setError(getText(R.string.error_signup));
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, "Error en la conexión intente de nuevo.", Toast.LENGTH_SHORT).show();
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

    private boolean validationSignUP(String username, String email, String password){

        if (username.isEmpty()){
            inputUsername.setError(getText(R.string.error_empty));
        } else if (email.isEmpty()){
            inputEmail.setError(getText(R.string.error_empty));
        }else if (password.isEmpty()){
            inputPassword.setError(getText(R.string.error_empty));
        } else {
            return true;
        }
        progressBar.setVisibility(View.GONE);
        return false;
    }
}


