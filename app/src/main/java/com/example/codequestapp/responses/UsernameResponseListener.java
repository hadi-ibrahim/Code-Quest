package com.example.codequestapp.responses;

import android.app.DownloadManager;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.codequestapp.models.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;

public class UsernameResponseListener implements Response.Listener<JSONArray> {

    private MutableLiveData<ResponseMessage> data;

    public UsernameResponseListener() {
        data = new MutableLiveData<ResponseMessage>();
    }

    public LiveData<ResponseMessage> getData() {
        return data;
    }

    @Override
    public void onResponse(JSONArray response) {
        User[] users = new Gson().fromJson(response.toString(), User[].class);
        ResponseMessage message;
        if (users.length>0) {
            message = new ResponseMessage("200", "Username already exists.", false);
        }
        else message = new ResponseMessage("200", "", true);
        data.postValue(message);
    }
}
