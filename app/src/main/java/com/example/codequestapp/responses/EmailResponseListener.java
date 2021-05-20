package com.example.codequestapp.responses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.example.codequestapp.models.User;
import com.google.gson.Gson;

import org.json.JSONArray;

public class EmailResponseListener implements Response.Listener<JSONArray> {

    private MutableLiveData<ResponseMessage> data;

    public EmailResponseListener() {
        data = new MutableLiveData<ResponseMessage>();
    }

    @Override
    public void onResponse(JSONArray response) {
        User[] users = new Gson().fromJson(response.toString(), User[].class);
        ResponseMessage message;
        if (users.length>0) {
            message = new ResponseMessage("200", "Email already exists.", false);
        }
        else message = new ResponseMessage("200", "", true);
        data.postValue(message);

    }

    public LiveData<ResponseMessage> getData() {
        return data;
    }
}
