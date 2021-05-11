package com.example.codequestapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.RequestQueue;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.models.User;
import com.example.codequestapp.requests.LoginPostRequest;
import com.example.codequestapp.requests.QuestsGetRequest;
import com.example.codequestapp.requests.RequestQueueSingleton;
import com.example.codequestapp.requests.SignupPostRequest;
import com.example.codequestapp.responses.ResponseMessage;

import java.util.ArrayList;

public class SignupViewModel extends AndroidViewModel {

    private LiveData<ResponseMessage> data;
    private SignupPostRequest request;
    private RequestQueue queue = RequestQueueSingleton.getInstance().getRequestQueue();

    public SignupViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        request = new SignupPostRequest();
        data = request.getData();
    }

    public LiveData<ResponseMessage> getData() {
        return data;
    }

    public void signUp(User user) {
        request.setUser(user);
        queue.add(request);
    }
}
