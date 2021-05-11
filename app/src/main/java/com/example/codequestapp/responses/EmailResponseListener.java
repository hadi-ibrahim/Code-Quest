package com.example.codequestapp.responses;

import android.app.DownloadManager;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.models.User;
import com.example.codequestapp.ui.quests.QuestCardAdapter;
import com.google.android.material.textfield.TextInputLayout;
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
