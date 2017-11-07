package com.liaobusi.example;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.liaobusi.easyobserve.EasyObserve;
import com.liaobusi.example.databinding.ActivitySampleBinding;
import com.liaobusi.runtime.Observe;

public class SampleActivity extends AppCompatActivity {

    ActivitySampleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample);
        EasyObserve.observe(this);
        binding.setCallback(mCallback);
    }

    @Observe("user")
    public void observeUser(User user) {
        binding.setUser(user);
    }

    private Callback mCallback = new Callback() {
        @Override
        public void clickMe() {

        }
    };


}
