package com.example.codequestapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.RequestQueue;
import com.example.codequestapp.models.User;
import com.example.codequestapp.requests.EmailExistGetRequest;
import com.example.codequestapp.requests.ProfileGetRequest;
import com.example.codequestapp.requests.RequestQueueSingleton;
import com.example.codequestapp.responses.ResponseMessage;

public class ProfileViewModel extends AndroidViewModel {
    private LiveData<User> data;
    private ProfileGetRequest request;
    private RequestQueue queue = RequestQueueSingleton.getInstance().getRequestQueue();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        request = new ProfileGetRequest();
        data = request.getData();
    }

    public LiveData<User> getData() {
        return data;
    }

    public void getProfileInfo() {
        queue.add(request);
    }
}
