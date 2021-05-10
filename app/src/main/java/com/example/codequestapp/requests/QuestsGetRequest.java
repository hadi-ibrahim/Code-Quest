package com.example.codequestapp.requests;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.QuestResponseListener;

import org.json.JSONArray;

import java.util.ArrayList;

public class QuestsGetRequest extends JsonArrayRequest {

    private LiveData<ArrayList<Quest>> questsData;
    private QuestResponseListener listener;

    public QuestsGetRequest(int method, String url, @Nullable JSONArray jsonRequest, Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.listener = (QuestResponseListener) listener;
        questsData = this.listener.getLiveData();
    }

    public QuestsGetRequest() {
        this(Method.GET, RequestUtil.BASE_URL + "api/quests", null, new QuestResponseListener(), new ErrorResponseListener());
    }

    public LiveData<ArrayList<Quest>> getQuestsData() {
        return questsData;
    }
}
