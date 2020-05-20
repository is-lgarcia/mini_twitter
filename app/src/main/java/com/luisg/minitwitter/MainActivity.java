package com.luisg.minitwitter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    ImageView imvLogo;
    TextInputLayout inputEmail, inputPassword;
    Button btnLogin, btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        findView();

    }

    private void findView() {
        imvLogo = findViewById(R.id.image_logo);
        inputEmail = findViewById(R.id.input_email_login);
        inputPassword = findViewById(R.id.input_password_login);
        btnLogin = findViewById(R.id.btn_login);
        btnCreateAccount = findViewById(R.id.btn_create_account);
    }

    public void onClickLogin(View view) {
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
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
