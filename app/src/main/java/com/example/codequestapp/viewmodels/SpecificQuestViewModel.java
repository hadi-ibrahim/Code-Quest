package com.example.codequestapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.RequestQueue;
import com.example.codequestapp.models.CategoryProgress;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.requests.ProgressGetRequest;
import com.example.codequestapp.requests.QuestGetRequest;
import com.example.codequestapp.requests.RequestQueueSingleton;
import com.example.codequestapp.requests.SolveQuestPostRequest;
import com.example.codequestapp.responses.ResponseMessage;

import java.util.ArrayList;

public class SpecificQuestViewModel extends AndroidViewModel {
    private LiveData<Quest> data;
    private QuestGetRequest request;
    private LiveData<ResponseMessage> solveResponse;
    private SolveQuestPostRequest solveRequest;
    private RequestQueue queue = RequestQueueSingleton.getInstance().getRequestQueue();

    public SpecificQuestViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        request = new QuestGetRequest();
        solveRequest = new SolveQuestPostRequest();
        solveResponse = solveRequest.getData();
        data = request.getData();
    }

    public LiveData<Quest> getData() {
        return data;
    }

    public void getPuzzlesAndQuestions(int id)
    {
        request.setId(id);
        queue.add(request);
    }

    public LiveData<ResponseMessage> getSolveResponse() {
        return solveResponse;
    }

    public void solve(Quest quest) {
        solveRequest.setQuest(quest);
        queue.add(solveRequest);
    }
}
