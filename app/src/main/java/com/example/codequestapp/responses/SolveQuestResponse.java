package com.example.codequestapp.responses;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.example.codequestapp.R;
import com.example.codequestapp.ui.registration.WelcomeFragment;
import com.example.codequestapp.utils.LoginManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class SolveQuestResponse implements Response.Listener<JSONObject> {

    private MutableLiveData<ResponseMessage> data;

    public MutableLiveData<ResponseMessage> getData() {
        return data;
    }

    public SolveQuestResponse() {
        data = new MutableLiveData<ResponseMessage>();
    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson = new Gson();
        ResponseMessage message = gson.fromJson(response.toString(), ResponseMessage.class);
        System.out.println(message.isSuccess());
        data.postValue(message);
    }
}
