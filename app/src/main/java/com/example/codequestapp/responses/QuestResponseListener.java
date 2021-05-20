package com.example.codequestapp.responses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.example.codequestapp.models.Quest;
import com.google.gson.Gson;

import org.json.JSONArray;

public class QuestResponseListener implements Response.Listener<String> {
    private MutableLiveData<Quest> data;

    public QuestResponseListener() {
        data = new MutableLiveData<Quest>();
    }

    @Override
    public void onResponse(String response) {
        System.out.println(response);
        Gson gson = new Gson();
        Quest quest = gson.fromJson(response, Quest.class);
        data.postValue(quest);
    }

    public LiveData<Quest> getData() {
        return data;
    }
}
