package com.example.codequestapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.RequestQueue;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.requests.QuestsGetRequest;
import com.example.codequestapp.requests.RequestQueueSingleton;

import java.util.ArrayList;

public class QuestViewModel extends AndroidViewModel {

    private LiveData<ArrayList<Quest>> data;
    private QuestsGetRequest request;
    private RequestQueue queue = RequestQueueSingleton.getInstance().getRequestQueue();

    public QuestViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        request = new QuestsGetRequest();
        data = request.getQuestsData();
    }

    public LiveData<ArrayList<Quest>> getData() {
        return data;
    }

    public void getQuests() {
        queue.add(request);
    }
}
