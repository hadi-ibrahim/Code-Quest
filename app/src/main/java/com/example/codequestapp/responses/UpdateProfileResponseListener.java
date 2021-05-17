package com.example.codequestapp.responses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONArray;

public class UpdateProfileResponseListener implements Response.Listener<String> {
    private MutableLiveData<ResponseMessage> data;

    public UpdateProfileResponseListener() {
        data = new MutableLiveData<ResponseMessage>();
    }

    @Override
    public void onResponse(String response) {
        Gson gson = new Gson();
        ResponseMessage message = gson.fromJson(response, ResponseMessage.class);
        data.postValue(message);
    }

    public LiveData<ResponseMessage> getData() {
        return data;
    }
}
