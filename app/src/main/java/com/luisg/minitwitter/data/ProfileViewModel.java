package com.luisg.minitwitter.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.luisg.minitwitter.retrofit.request.RequestUserProfile;
import com.luisg.minitwitter.retrofit.response.ResponseUserProfile;

public class ProfileViewModel extends AndroidViewModel {
    public ProfileRepository profileRepository;
    public LiveData<ResponseUserProfile> userProfileLiveData;
    public LiveData<String> photoProfile;


    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepository();
        userProfileLiveData = profileRepository.getProfile();
        photoProfile = profileRepository.getPhotProfile();
    }

    public void updateProfile(RequestUserProfile requestUserProfile){
        profileRepository.updateProfile(requestUserProfile);
    }

    public void uploadPhoto(String photo){
        profileRepository.uploadProfilePhoto(photo);
    }
}
