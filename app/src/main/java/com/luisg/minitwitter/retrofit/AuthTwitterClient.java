package com.luisg.minitwitter.retrofit;

import com.luisg.minitwitter.common.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthTwitterClient {
    private static AuthTwitterClient instance = null;
    private AuthTwitterService authTwitterService;
    private Retrofit retrofit;

    public AuthTwitterClient() {

        //Incluir en la cabecera de la pertición el TOKEN que autoriza al usuario
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new AuthInteceptor());

        OkHttpClient client = okHttpClient.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_MINITWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        authTwitterService = retrofit.create(AuthTwitterService.class);
    }


    //Patrón Singleton
    public static AuthTwitterClient getInstance(){
        if (instance== null){

            instance = new AuthTwitterClient();
        }
        return instance;
    }

    public AuthTwitterService getAuthTwitterService(){
        return authTwitterService;
    }
}
