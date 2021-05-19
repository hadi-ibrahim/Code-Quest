package com.example.codequestapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.RequestQueue;
import com.example.codequestapp.models.CategoryProgress;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.requests.ProgressGetRequest;
import com.example.codequestapp.requests.RequestQueueSingleton;

import java.util.ArrayList;

public class AchievementsViewModel extends AndroidViewModel {
    private LiveData<ArrayList<CategoryProgress>> data;
    private ProgressGetRequest request;
    private RequestQueue queue = RequestQueueSingleton.getInstance().getRequestQueue();

    public AchievementsViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        request = new ProgressGetRequest();
        data = request.getData();
    }

    public LiveData<ArrayList<CategoryProgress>> getData() {
        return data;
    }

    public void getProgress() {
        queue.add(request);
    }
}
