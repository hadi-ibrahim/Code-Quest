package com.example.codequestapp.responses;

import android.app.DownloadManager;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.ui.quests.QuestCardAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestResponseListener implements Response.Listener<JSONArray> {

    private MutableLiveData<ArrayList<Quest>> questsData;

    public QuestResponseListener() {
        this.questsData = new MutableLiveData<ArrayList<Quest>>();
    }

    @Override
    public void onResponse(JSONArray response) {
        Quest[] quests = new Gson().fromJson(response.toString(), Quest[].class);
        questsData.postValue(new ArrayList<Quest>(Arrays.asList(quests)));
    }

    public LiveData<ArrayList<Quest>> getLiveData() {
        return questsData;
    }
}
