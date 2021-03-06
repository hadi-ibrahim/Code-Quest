package com.example.codequestapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.RequestQueue;
import com.example.codequestapp.requests.EmailExistGetRequest;
import com.example.codequestapp.requests.QuestsGetRequest;
import com.example.codequestapp.requests.RequestQueueSingleton;
import com.example.codequestapp.responses.ResponseMessage;

public class EmailExistViewModel extends AndroidViewModel {
    private LiveData<ResponseMessage> data;
    private EmailExistGetRequest request;
    private RequestQueue queue = RequestQueueSingleton.getInstance().getRequestQueue();

    public EmailExistViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        request = new EmailExistGetRequest();
        data = request.getData();
    }

    public LiveData<ResponseMessage> getData() {
        return data;
    }

    public void validateEmail(String email) {
        request.setEmail(email);
        queue.add(request);
    }
}
