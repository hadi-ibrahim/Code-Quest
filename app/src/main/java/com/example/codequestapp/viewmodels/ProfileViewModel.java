package com.example.codequestapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.RequestQueue;
import com.example.codequestapp.models.User;
import com.example.codequestapp.requests.EmailExistGetRequest;
import com.example.codequestapp.requests.ProfileGetRequest;
import com.example.codequestapp.requests.ProfilePicPutRequest;
import com.example.codequestapp.requests.ProfilePutRequest;
import com.example.codequestapp.requests.RequestQueueSingleton;
import com.example.codequestapp.responses.ResponseMessage;

import java.io.File;

public class ProfileViewModel extends AndroidViewModel {
    private LiveData<User> data;
    private LiveData<ResponseMessage> updateMessage;
    private LiveData<ResponseMessage> picUpdateMessage;
    private ProfileGetRequest request;
    private ProfilePutRequest putRequest;
    private ProfilePicPutRequest picPutRequest;

    private RequestQueue queue = RequestQueueSingleton.getInstance().getRequestQueue();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        request = new ProfileGetRequest();
        data = request.getData();
        putRequest = new ProfilePutRequest();
        updateMessage = putRequest.getData();
        picPutRequest = new ProfilePicPutRequest();
        picUpdateMessage = picPutRequest.getData();
    }

    public LiveData<User> getData() {
        return data;
    }

    public LiveData<ResponseMessage> getUpdateMessage() {
        return updateMessage;
    }
    public LiveData<ResponseMessage> getPicUpdateMessage() {
        return picUpdateMessage;
    }
    public void getProfileInfo() {
        queue.add(request);
    }

    public void updateProfileInfo(User user) {
        putRequest.setUser(user);
        queue.add(putRequest);
    }

    public void updateProfilePic(File file) {
        picPutRequest.setFile(file);
        queue.add(picPutRequest);
    }
}
