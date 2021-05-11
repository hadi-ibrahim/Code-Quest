package com.example.codequestapp.responses;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.example.codequestapp.R;
import com.example.codequestapp.ui.registration.WelcomeFragment;
import com.example.codequestapp.utils.LoginManager;
import com.google.gson.Gson;

public class RegistrationResponseListener implements Response.Listener<String> {

    private MutableLiveData<ResponseMessage> data;

    public MutableLiveData<ResponseMessage> getData() {
        return data;
    }

    public RegistrationResponseListener() {
        data = new MutableLiveData<ResponseMessage>();
    }

    @Override
    public void onResponse(String response) {
        Gson gson = new Gson();
        ResponseMessage message = gson.fromJson(response, ResponseMessage.class);
        data.postValue(message);
    }
}
