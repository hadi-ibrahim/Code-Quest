package com.example.codequestapp.responses;

import android.app.DownloadManager;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.ui.quests.QuestCardAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;

public class QuestResponseListener<JSONArray> implements Response.Listener<JSONArray> {

    private RecyclerView questCards;
    private Context context;

    public QuestResponseListener(RecyclerView cards, Context context) {
        this.questCards = cards;
        this.context = context;
    }

    @Override
    public void onResponse(JSONArray response) {
        Quest[] quests = new Gson().fromJson(response.toString(), Quest[].class);
        QuestCardAdapter adapter = new QuestCardAdapter(quests, context);
        questCards.setAdapter(adapter);
        questCards.setLayoutManager(new LinearLayoutManager(context));

    }
}
