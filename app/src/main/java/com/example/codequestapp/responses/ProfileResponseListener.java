package com.example.codequestapp.responses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.example.codequestapp.models.User;
import com.google.gson.Gson;

import org.json.JSONArray;

public class ProfileResponseListener implements Response.Listener<JSONArray> {
    private MutableLiveData<User> data;

    public ProfileResponseListener() {
        data = new MutableLiveData<User>();
    }

    @Override
    public void onResponse(JSONArray response) {
        User[] users = new Gson().fromJson(response.toString(), User[].class);
        User user = null;
        ResponseMessage message;
        if (users.length>0) {
            user = users[0];
        }
        data.postValue(user);
    }

    public LiveData<User> getData() {
        return data;
    }
}
