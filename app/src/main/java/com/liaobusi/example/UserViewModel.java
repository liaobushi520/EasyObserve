package com.liaobusi.example;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.liaobusi.runtime.Observable;

/**
 * Created by liaozhongjun on 2017/11/3.
 */

public class UserViewModel extends ViewModel {
    @Observable("user")
    public MediatorLiveData<User> userLiveData = new MediatorLiveData<>();

    public UserViewModel(){
        userLiveData.postValue(new User("liao"));
    }
}
