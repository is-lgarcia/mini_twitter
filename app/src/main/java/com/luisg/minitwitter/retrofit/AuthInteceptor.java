package com.luisg.minitwitter.retrofit;

import com.luisg.minitwitter.common.Constants;
import com.luisg.minitwitter.common.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInteceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = SharedPreferencesManager.getSomeStringValue(Constants.PREF_TOKEN);
        Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer "+ token).build();
        return chain.proceed(request);
    }
}
