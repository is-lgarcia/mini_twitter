package com.luisg.minitwitter.retrofit;

import com.luisg.minitwitter.common.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MIniTwitterClient {
    private static MIniTwitterClient instance = null;
    private MiniTwitterService miniTwitterService;
    private Retrofit retrofit;

    public MIniTwitterClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_MINITWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        miniTwitterService = retrofit.create(MiniTwitterService.class);
    }


    //Patrón Singleton
    public static MIniTwitterClient getInstance(){
        if (instance== null){

            instance = new MIniTwitterClient();
        }
        return instance;
    }

    public MiniTwitterService getMiniTwitterService(){
        return miniTwitterService;
    }
}
